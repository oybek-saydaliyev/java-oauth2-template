package uz.project.template.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import uz.project.template.oauth.CustomOAuth2UserService;
import uz.project.template.oauth.CustomOauth2AuthorizationRequestResolver;
import uz.project.template.security.JwtFiler;
import uz.project.template.oauth.OAuth2LoginSuccessHandler;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true
)
public class SecurityConfig {
    //enter your white list
    private static final String[] WHITE_LIST = {
            "/**"
    };

    private final JwtFiler jwtFiler;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;

    public SecurityConfig(JwtFiler jwtFiler, CustomOAuth2UserService customOAuth2UserService, OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler) {
        this.jwtFiler = jwtFiler;
        this.customOAuth2UserService = customOAuth2UserService;
        this.oAuth2LoginSuccessHandler = oAuth2LoginSuccessHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, ClientRegistrationRepository clientRegistrationRepository) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(
                authorizeRequests ->
                {
                    authorizeRequests
                            .requestMatchers(WHITE_LIST).permitAll()
                            .anyRequest().authenticated();
                });
        http.oauth2Login(oauth -> {
            oauth.userInfoEndpoint(info ->
                    info.userService(customOAuth2UserService))
                    .successHandler(oAuth2LoginSuccessHandler);
            oauth.authorizationEndpoint(authorizationEndpoint -> {
               authorizationEndpoint.authorizationRequestResolver(
                       new CustomOauth2AuthorizationRequestResolver(clientRegistrationRepository)
               );
            });

        });
        http.sessionManagement(sessionManagement ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(jwtFiler, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
