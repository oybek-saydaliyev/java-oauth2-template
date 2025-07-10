package uz.project.template.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.project.template.base.ApiResponse;
import uz.project.template.dto.UserDto;
import uz.project.template.dto.UserResponseDto;
import uz.project.template.entity.AuthUserEntity;
import uz.project.template.repository.UserRepository;
import uz.project.template.security.JwtUtil;
import uz.project.template.service.AuthService;
import uz.project.template.utils.ResMessages;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public HashMap<String, Object> login(UserDto dto) {
        HashMap<String, Object> response = new HashMap<>();
        response.put("code", 0);
        response.put("message", null);
        response.put("object", null);

        if (dto.getUsername() == null || dto.getPassword() == null) {
            response.put("message", ResMessages.INVALID_INPUT);
            response.put("code", 1);
            response.put("object", null);
            return response;
        }

        Optional<AuthUserEntity> byUsername = userRepository.findByUsername(dto.getUsername());
        if (byUsername.isEmpty()) {
            response.put("message", ResMessages.USER_NOT_FOUND);
            response.put("code", 2);
            response.put("object", null);
        }

        AuthUserEntity user = byUsername.get();
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            response.put("message", ResMessages.INCORRECT_PASSWORD);
            response.put("code", 3);
            response.put("object", null);
            return response;
        }

        String accessToken = jwtUtil.generateAccessToken(user);
        String refreshToken = jwtUtil.generateRefreshToken(user);
        UserResponseDto userResponseDto = UserResponseDto.toDto(new UserResponseDto(), user);

        response.put("message", ResMessages.SUCCESS);
        response.put("code", 200);
        response.put("object", userResponseDto);
        response.put("accessToken", accessToken);
        response.put("refreshToken", refreshToken);
        return response;
    }

    @Override
    public HashMap<String, Object> loginWithToken(String refreshToken) {
        HashMap<String, Object> response = new HashMap<>();
        response.put("code", 0);
        response.put("message", null);

        if (!jwtUtil.validateRefreshToken(refreshToken)) {
            response.put("code", 1);
            response.put("message", ResMessages.TOKEN_VALIDATION_FAILED);
            return response;
        }

        String accessToken = jwtUtil.generateAccessToken(refreshToken);
        response.put("message", ResMessages.SUCCESS);
        response.put("code", 200);
        response.put("accessToken", accessToken);
        return response;
    }

    @Override
    public HashMap<String, Object> getMe(String accessToken) {
        HashMap<String, Object> response = new HashMap<>();
        response.put("code", 0);
        response.put("message", null);
        response.put("object", null);

        if (!jwtUtil.validateAccessToken(accessToken)) {
            response.put("code", 1);
            response.put("message", ResMessages.TOKEN_VALIDATION_FAILED);
            return response;
        }

        String username = jwtUtil.extractUsername(accessToken);

        Optional<AuthUserEntity> byUsername = userRepository.findByUsername(username);
        if (byUsername.isEmpty()) {
            response.put("message", ResMessages.USER_NOT_FOUND);
            response.put("code", 2);
            return response;
        }

        AuthUserEntity user = byUsername.get();
        UserResponseDto userResponseDto = UserResponseDto.toDto(new UserResponseDto(), user);

        response.put("message", ResMessages.SUCCESS);
        response.put("code", 200);
        response.put("object", userResponseDto);
        return response;
    }
}
