package org.dclou.example.demogpb.order.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by msnikitin on 20.04.2017.
 */
public class AuthenticationSuccessHandler4PasswordGrant extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        // This is actually not an error, but an OK message. It is sent to avoid redirects.
        response.sendError(HttpServletResponse.SC_OK);
    }
}
