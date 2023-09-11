package teamdropin.server.security.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/oauth-login")
public class OAuthController {

    @Value("${spring.security.oauth2.client.registration.google.redirectUrl}")
    private String redirectURI;

    @GetMapping("/google")
    public void oauthLogin(HttpServletResponse response) throws IOException {
        response.sendRedirect(redirectURI);
    }
}