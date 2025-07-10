package uz.project.template.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.project.template.base.ApiResponse;
import uz.project.template.dto.UserDto;
import uz.project.template.dto.UserResponseDto;
import uz.project.template.entity.AuthUserEntity;
import uz.project.template.repository.UserRepository;
import uz.project.template.service.UserService;
import uz.project.template.utils.ResMessages;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public ApiResponse<UserResponseDto> createUser(UserDto userDto) {
        try{
            AuthUserEntity user = UserDto.toEntity(userDto, new AuthUserEntity());
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            AuthUserEntity save = userRepository.save(user);
            UserResponseDto userResponseDto = UserResponseDto.toDto(new UserResponseDto(), save);
            return new ApiResponse<>(true, userResponseDto, ResMessages.SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new ApiResponse<>(false, e.getMessage());
        }
    }


    @Override
    public ApiResponse<UserResponseDto> updateUser(UserDto userDto) {
        return null;
    }

    @Override
    public ApiResponse<AuthUserEntity> getUser(String id) {
        return null;
    }

}
