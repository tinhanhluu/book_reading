package com.book_reading.service;

import com.book_reading.dto.request.UserCreationRequest;
import com.book_reading.dto.response.UserResponse;
import com.book_reading.entity.User;
import com.book_reading.enums.Roles;
import com.book_reading.exception.AppException;
import com.book_reading.exception.ErrorCode;
import com.book_reading.mapper.UserMapper;
import com.book_reading.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;

    public UserResponse registerUser(UserCreationRequest request) {
        if(userRepository.existsByName(request.getName())){
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        var userPassword = passwordEncoder.encode(request.getPassword());

        User user = User.builder()
                .name(request.getName())
                .password(userPassword)
                .birthday(request.getBirthday())
                .role(Roles.ROLE_USER)
                .createdAt(new Date())
                .build();
        return userMapper.toUserResponse(userRepository.save(user));
    }
}
