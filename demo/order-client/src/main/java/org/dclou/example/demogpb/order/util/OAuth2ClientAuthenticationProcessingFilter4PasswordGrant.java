package org.dclou.example.demogpb.order.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by msnikitin on 20.04.2017.
 */
public class OAuth2ClientAuthenticationProcessingFilter4PasswordGrant extends OAuth2ClientAuthenticationProcessingFilter {

    public OAuth2ClientAuthenticationProcessingFilter4PasswordGrant(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);

        setAuthenticationSuccessHandler(new AuthenticationSuccessHandler4PasswordGrant());
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        String username = request.getHeader("username");
        String password = request.getHeader("password");
        this.restTemplate.getOAuth2ClientContext().getAccessTokenRequest().set("username", username);
        this.restTemplate.getOAuth2ClientContext().getAccessTokenRequest().set("password", password);
        return super.attemptAuthentication(request, response);
    }
}
