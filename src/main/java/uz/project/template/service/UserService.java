package uz.project.template.service;

import uz.project.template.base.ApiResponse;
import uz.project.template.dto.UserDto;
import uz.project.template.dto.UserResponseDto;
import uz.project.template.entity.AuthUserEntity;

public interface UserService {

    ApiResponse<UserResponseDto> createUser(UserDto userDto);
    ApiResponse<UserResponseDto> updateUser(UserDto userDto);
    ApiResponse<AuthUserEntity> getUser(String id);

}
