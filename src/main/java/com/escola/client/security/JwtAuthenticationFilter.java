package com.escola.client.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j // Adicionar anotação para logging
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final List<String> PUBLIC_URLS = Arrays.asList(
//            "/graphql", // Se /graphql é público para algumas operações, considere um filtro diferente ou lógica dentro do resolver
            "/graphiql",
            "/playground",
            "/" // Raiz
    );
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String requestUri = request.getRequestURI();

        // **Important:** If your GraphQL endpoint (`/graphql`) is generally
        // protected, you should NOT include it in PUBLIC_URLS.
        // If it's public but some operations require authentication,
        // you'll handle authentication checks within your GraphQL resolvers.
        // For now, assuming you want authentication for /graphql unless it's a login mutation.

        if (PUBLIC_URLS.contains(requestUri)) {
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        String userEmail = null;

        // 1. Check for Authorization header presence and format
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // If no token or malformed header, simply pass to next filter.
            // Spring Security will then handle it (e.g., return 401 if authentication
            // is required later in the filter chain, or allow if public).
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7); // Extract the token after "Bearer "

        try {
            // 2. Extract username from JWT
            userEmail = jwtService.extractUsername(jwt);

            // 3. Authenticate if username found and not already authenticated
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = null;
                try {
                    userDetails = this.userDetailsService.loadUserByUsername(userEmail);
                } catch (UsernameNotFoundException e) {
                    log.warn("User not found for JWT: {}", userEmail);
                    // Do not break the filter chain, let it continue without authentication
                    // This scenario means a valid token but for a non-existent user.
                    // The request will proceed unauthenticated.
                    filterChain.doFilter(request, response);
                    return;
                }


                if (jwtService.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null, // Credentials should be null after authentication
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    log.debug("User '{}' authenticated successfully.", userEmail);
                } else {
                    log.warn("Invalid JWT for user: {}", userEmail);
                    // Token is invalid for the user (e.g., signature mismatch, altered token)
                    // Set 401 Unauthorized explicitly
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    response.getWriter().write("Invalid or expired token.");
                    return; // Stop processing the filter chain for this request
                }
            }
        } catch (MalformedJwtException e) {
            log.warn("Invalid JWT format (MalformedJwtException): {}", e.getMessage());
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Invalid token format.");
            return; // Stop processing
        } catch (SignatureException e) {
            log.warn("Invalid JWT signature (SignatureException): {}", e.getMessage());
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Invalid token signature.");
            return; // Stop processing
        } catch (ExpiredJwtException e) {
            log.warn("JWT expired (ExpiredJwtException): {}", e.getMessage());
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Token has expired.");
            return; // Stop processing
        } catch (Exception e) { // Catch any other unexpected exceptions during JWT processing
            log.error("An unexpected error occurred during JWT processing: {}", e.getMessage(), e);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value()); // Or UNAUTHORIZED if you prefer
            response.getWriter().write("An unexpected error occurred during authentication.");
            return; // Stop processing
        }

        // Continue the filter chain if authentication was successful or if the request
        // did not require authentication (e.g., token was null/empty or user not found,
        // and the URL wasn't explicitly public)
        filterChain.doFilter(request, response);
    }
}