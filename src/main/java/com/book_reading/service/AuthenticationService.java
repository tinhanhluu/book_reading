package com.book_reading.service;

import com.book_reading.dto.request.AuthenticationRequest;
import com.book_reading.dto.request.IntrospectRequest;
import com.book_reading.dto.response.AuthenticationResponse;
import com.book_reading.dto.response.IntrospectResponse;
import com.book_reading.entity.InvalidToken;
import com.book_reading.entity.User;
import com.book_reading.enums.Roles;
import com.book_reading.exception.AppException;
import com.book_reading.exception.ErrorCode;
import com.book_reading.repository.InvalidTokenRepository;
import com.book_reading.repository.UserRepository;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.json.jackson2.JacksonFactory;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthenticationService {
    UserRepository userRepository;
    InvalidTokenRepository invalidTokenRepository;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String signerKey;

    String GOOGLE_CLIENT_ID = "72630427087-udbe6nq72tmc2il5v28l4sbhhr1s8qdm.apps.googleusercontent.com";

    @NonFinal
    @Value("${jwt.valid-duration}")
    protected long VALID_DURATION;

    @NonFinal
    @Value("${jwt.refreshable-duration}")
    protected long REFRESH_DURATION;

    @Autowired
    PasswordEncoder passwordEncoder;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var user = userRepository.findByName(request.getName())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOTFOUND));

        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if(!authenticated) {
            throw new AppException(ErrorCode.WRONG_CREDENTIALS);
        }

        return AuthenticationResponse.builder()
                .success(true)
                .token(generateToken(user))
                .name(user.getName())
                .avatarUrl(user.getAvatarUrl())
                .isVip(user.isVip())
                .build();
    }

    public AuthenticationResponse authenticateWithGoogle(String idTokenString) {

        GoogleIdToken.Payload payload = verifyGoogleToken(idTokenString);

        String email = payload.getEmail();
        String name = (String) payload.get("name");
        String avatar = (String) payload.get("picture");

        log.info("aud: {}", payload.getAudience());
        log.info("iss: {}", payload.getIssuer());

        // Nếu người dùng đã tồn tại -> lấy
        User user = userRepository.findByEmail(email)
                .orElseGet(() -> {
                    // Nếu chưa tồn tại -> tạo mới
                    User newUser = new User();
                    newUser.setEmail(email);
                    newUser.setName(name);
                    newUser.setAvatarUrl(avatar);
                    newUser.setVip(false);
                    newUser.setRole(Roles.ROLE_USER);
                    newUser.setPassword(passwordEncoder.encode(UUID.randomUUID().toString())); // password ngẫu nhiên
                    return userRepository.save(newUser);
                });

        // Tạo JWT token
        String token = generateToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .success(true)
                .name(user.getName())
                .avatarUrl(user.getAvatarUrl())
                .isVip(user.isVip())
                .build();
    }

    private GoogleIdToken.Payload verifyGoogleToken(String idTokenString) {
        try {
            JacksonFactory jacksonFactory = new JacksonFactory();
            NetHttpTransport transport = new NetHttpTransport();

            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jacksonFactory)
                    .setAudience(Collections.singletonList(GOOGLE_CLIENT_ID))
                    .setIssuer("https://accounts.google.com")
                    .build();

            log.info("🔍 Verifying Google ID Token...");
            log.info("Received idToken from frontend: {}", idTokenString);
            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken != null) {
                log.info("Google ID Token verified successfully. Email: {}", idToken.getPayload().getEmail());
                return idToken.getPayload();
            } else {
                log.error("Google ID Token verify failed!");
                throw new AppException(ErrorCode.UNAUTHENTICATED);
            }
        } catch (Exception e) {
            log.error("❌ Exception during Google token verification: ", e);
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
    }

    private String generateToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .issuer("tinhanh.com")
                .subject(user.getName())
                .jwtID(UUID.randomUUID().toString())
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli()))
                .claim("scope", user.getRole().toString())
                .build();

        Payload payload = new Payload(claimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(signerKey.getBytes(StandardCharsets.UTF_8)));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
    }

    private SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(signerKey);

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = (isRefresh)
                ? new Date(signedJWT.getJWTClaimsSet().getIssueTime().toInstant().plus(REFRESH_DURATION, ChronoUnit.SECONDS).toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();

        var verify = signedJWT.verify(verifier);

        if(!(verify && expiryTime.after(new Date()))) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        if(invalidTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID())) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        return signedJWT;
    }

    public IntrospectResponse introspect(IntrospectRequest request) throws ParseException, JOSEException {
        var token = request.getToken();
        boolean isValid = true;

        try {
            verifyToken(token, false);
        } catch (ParseException e) {
            isValid = false;
        }

        return IntrospectResponse.builder()
                .valid(isValid)
                .build();
    }

    // nên sửa lại sau này, nên lấy token từ header chứ không lấy từ body từ phái client gửi lên
    public void logout(IntrospectRequest request) throws ParseException, JOSEException {
        try {
            SignedJWT signedJWT = verifyToken(request.getToken(), true);
            var jti = signedJWT.getJWTClaimsSet().getJWTID();
            var expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

            InvalidToken invalidToken = InvalidToken.builder()
                    .expiryTime(expiryTime)
                    .tokenId(jti)
                    .build();

            invalidTokenRepository.save(invalidToken);

        } catch (AppException e) {
            log.info("Token is expired");
        }
    }
}
