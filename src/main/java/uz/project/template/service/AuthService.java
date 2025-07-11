package uz.project.template.service;

import uz.project.template.dto.UserDto;

import java.util.HashMap;

public interface AuthService {

    HashMap<String, Object> loginWithToken(String token);

    HashMap<String, Object> getMe(String accessToken);
}
