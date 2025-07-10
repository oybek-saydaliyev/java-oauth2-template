package uz.project.template.controlller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.project.template.base.ApiResponse;
import uz.project.template.dto.UserDto;
import uz.project.template.service.AuthService;
import uz.project.template.service.UserService;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody UserDto userDto) {
        return ApiResponse.controller(userService.createUser(userDto));
    }

    @GetMapping("/me")
    public HashMap<String, Object> getMe(String accessToken) {
        return authService.getMe(accessToken);
    }

}
