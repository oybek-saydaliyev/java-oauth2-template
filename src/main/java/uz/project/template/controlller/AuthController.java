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

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<HashMap<String,Object>> login(@RequestBody UserDto dto) {
        return ResponseEntity.ok(authService.login(dto));
    }

    @GetMapping("/login/token")
    public ResponseEntity<HashMap<String,Object>> loginToken(@RequestParam String token) {
        return ResponseEntity.ok(authService.loginWithToken(token));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/get")
    public String getTest() {
        return "Success";
    }
}
