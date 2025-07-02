package com.jobportal.malawi.security.jwt;

import com.jobportal.malawi.security.services.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        if (path.startsWith("/api/auth/")) {
            logger.debug("AuthTokenFilter: shouldNotFilter - Skipping filter for auth path: {}", path);
            return true; // Do not filter /api/auth/** paths
        }
        logger.debug("AuthTokenFilter: shouldNotFilter - Applying filter for path: {}", path);
        return false; // Apply filter for all other paths
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        logger.debug("AuthTokenFilter: Entering doFilterInternal for URI: {}", request.getRequestURI());
        try {
            String jwt = parseJwt(request);
            logger.debug("AuthTokenFilter: Parsed JWT: {}", jwt != null ? "[PRESENT]" : "[NOT PRESENT]");

            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                logger.debug("AuthTokenFilter: JWT is valid.");
                String username = jwtUtils.getUserNameFromJwtToken(jwt);
                logger.debug("AuthTokenFilter: Username from JWT: {}", username);

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                logger.debug("AuthTokenFilter: UserDetails loaded for: {}", userDetails.getUsername());

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
                logger.debug("AuthTokenFilter: SecurityContextHolder updated for user: {}", userDetails.getUsername());
            } else if (jwt != null) {
                logger.debug("AuthTokenFilter: JWT is NOT valid.");
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e.getMessage(), e);
        }

        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }

        return null;
    }
}