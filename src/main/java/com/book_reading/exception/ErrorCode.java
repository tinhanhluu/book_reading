package com.book_reading.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum ErrorCode {
    USER_NOTFOUND(404, "User you search not found", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(401, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    WRONG_CREDENTIALS(401, "Wrong credentials, maybe its your password", HttpStatus.UNAUTHORIZED),
    USER_EXISTED(409, "User you search existed", HttpStatus.CONFLICT),
    UNDEMANDING_USER(400, "User not match requirements", HttpStatus.BAD_REQUEST),
    AUTHOR_NOT_FOUND(404, "Author not found", HttpStatus.NOT_FOUND),
    NOT_FOUND(404, "Not found", HttpStatus.NOT_FOUND)
    ;

    ErrorCode(int code, String message, HttpStatusCode httpStatusCode) {
        this.code = code;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }

    int code;
    String message;
    HttpStatusCode httpStatusCode;

}
