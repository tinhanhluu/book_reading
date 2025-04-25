package com.book_reading.mapper;

import com.book_reading.dto.request.UserCreationRequest;
import com.book_reading.dto.response.UserResponse;
import com.book_reading.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);
    UserResponse toUserResponse(User user);
}
