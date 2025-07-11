package uz.project.template.controlller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.project.template.dto.UserDto;
import uz.project.template.service.AuthService;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/get")
    public String getTest() {
        return "Success";
    }

}
