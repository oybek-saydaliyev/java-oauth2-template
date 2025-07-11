package uz.project.template.controlller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.project.template.service.AuthService;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final AuthService authService;

    @GetMapping("/me")
    public HashMap<String, Object> getMe(String accessToken) {
        return authService.getMe(accessToken);
    }

}
