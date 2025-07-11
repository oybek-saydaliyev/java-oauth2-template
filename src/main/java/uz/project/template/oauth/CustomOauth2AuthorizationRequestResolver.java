package uz.project.template.oauth;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;

import java.util.HashMap;
import java.util.Map;

public class CustomOauth2AuthorizationRequestResolver implements OAuth2AuthorizationRequestResolver {

    private final DefaultOAuth2AuthorizationRequestResolver defaultRequestResolver;

    public CustomOauth2AuthorizationRequestResolver(ClientRegistrationRepository repo) {
        this.defaultRequestResolver = new DefaultOAuth2AuthorizationRequestResolver(repo,"/oauth2/authorization");
    }

    @Override
    public OAuth2AuthorizationRequest resolve(HttpServletRequest request) {
        OAuth2AuthorizationRequest original = this.defaultRequestResolver.resolve(request);
        return customize(original);
    }

    @Override
    public OAuth2AuthorizationRequest resolve(HttpServletRequest request, String clientRegistrationId) {
        OAuth2AuthorizationRequest original = defaultRequestResolver.resolve(request, clientRegistrationId);
        return customize(original);
    }

    private OAuth2AuthorizationRequest customize(OAuth2AuthorizationRequest request) {
        if (request == null) return null;

        Map<String, Object> extraParams = new HashMap<>(request.getAdditionalParameters());
        extraParams.put("prompt", "select_account");
        return OAuth2AuthorizationRequest.from(request)
                .additionalParameters(extraParams)
                .build();
    }
}
