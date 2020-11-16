package com.ds.project.apothecary.security;

import com.ds.project.apothecary.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The type Auth token filter.
 */
public class AuthTokenFilter
        extends OncePerRequestFilter {
    /**
     * The Jwt utils.
     */
    @Autowired
    private JwtUtils
            jwtUtils;
    /**
     * The User details service.
     */
    @Autowired
    private UserDetailsServiceImpl
            userDetailsService;

    /**
     * Do filter internal.
     *
     * @param request     the request
     * @param response    the response
     * @param filterChain the filter chain
     * @throws ServletException the servlet exception
     * @throws IOException      the io exception
     */
    @Override
    protected void doFilterInternal(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain filterChain)
            throws
            ServletException,
            IOException {
        try {
            String
                    jwt =
                    parseJwt(request);
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                String
                        username =
                        jwtUtils.getUserNameFromJwtToken(jwt);

                UserDetails
                        userDetails =
                        userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken
                        authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, null,
                                userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource()
                        .buildDetails(request));

                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Parse jwt string.
     *
     * @param request the request
     * @return the string
     */
    private String parseJwt(final HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth)
                && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring("Bearer ".length());
        }

        return null;
    }
}
