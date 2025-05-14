package com.book_reading.mapper;

import com.book_reading.dto.request.UserCreationRequest;
import com.book_reading.dto.request.UserUpdateRequest;
import com.book_reading.dto.response.UserResponse;
import com.book_reading.entity.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);
    UserResponse toUserResponse(User user);

    List<UserResponse> toAllUsersResponse(List<User> user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUser(UserUpdateRequest request, @MappingTarget User user);
}
