package com.ds.project.apothecary.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The type Auth entry point jwt.
 */
@Component
public class AuthEntryPointJwt
        implements AuthenticationEntryPoint {

    /**
     * Commence.
     *
     * @param request       the request
     * @param response      the response
     * @param authException the auth exception
     * @throws IOException      the io exception
     * @throws ServletException the servlet exception
     */
    @Override
    public void commence(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final AuthenticationException authException)
            throws
            IOException,
            ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: "
                + "Unauthorized");
    }
}
