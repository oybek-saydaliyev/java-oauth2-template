package uz.project.template.oauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import uz.project.template.security.JwtUtil;
import uz.project.template.utils.ResMessages;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public OAuth2LoginSuccessHandler(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User user = (OAuth2User) authentication.getPrincipal();

        String email = user.getAttribute("email");

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        String accessToken = jwtUtil.generateAccessToken(userDetails);
        String refreshToken = jwtUtil.generateRefreshToken(userDetails);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("access_token", accessToken );
        responseBody.put("refresh_token", refreshToken);
        responseBody.put("message", ResMessages.SUCCESS);

        ObjectMapper mapper = new ObjectMapper();
        String jsonResponse = mapper.writeValueAsString(responseBody);

        response.getWriter().write(jsonResponse);
    }
}
