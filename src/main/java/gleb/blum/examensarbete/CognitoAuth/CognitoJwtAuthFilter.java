package gleb.blum.examensarbete.CognitoAuth;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CognitoJwtAuthFilter extends OncePerRequestFilter {

    @Value("${security.oauth2.client.provider.cognito.issuerUri:#{null}}")
    private String issuerUri;
    
    @Autowired
    private TokenValidationService tokenValidationService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            // Extract the JWT token from the Authorization header
            String authorizationHeader = request.getHeader("Authorization");

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String token = authorizationHeader.substring(7);
                Claims claims = tokenValidationService.validateToken(token);
                
                if (claims != null) {
                    // Extract user information from claims
                    String username = claims.getSubject();
                    
                    // Extract groups/roles if available
                    List<String> groups = claims.get("cognito:groups", List.class);
                    List<SimpleGrantedAuthority> authorities = Collections.emptyList();
                    
                    if (groups != null) {
                        authorities = groups.stream()
                            .map(group -> new SimpleGrantedAuthority("ROLE_" + group.toUpperCase()))
                            .collect(Collectors.toList());
                    }
                    
                    // Create authentication token
                    UsernamePasswordAuthenticationToken authentication = 
                        new UsernamePasswordAuthenticationToken(username, null, authorities);
                    
                    // Set authentication in context
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (Exception e) {
            // Log the exception
            System.err.println("Error processing JWT token: " + e.getMessage());
            // Do not set authentication
        }

        // Continue the filter chain
        filterChain.doFilter(request, response);
    }
}
