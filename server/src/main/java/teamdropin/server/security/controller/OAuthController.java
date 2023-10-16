package teamdropin.server.security.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Tag(name = "8. OAuth2.0 API", description = "OAuth2.0 API Document")
@RestController
@RequestMapping("/oauth-login")
public class OAuthController {

    @Value("${spring.security.oauth2.client.registration.google.redirectUrl}")
    private String redirectURI;

    @Operation(summary = "구글 OAuthLogin EntryPoint API")
    @GetMapping("/google")
    public void oauthLogin(HttpServletResponse response) throws IOException {
        response.sendRedirect(redirectURI);
    }
}