package gleb.blum.examensarbete.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller for handling authentication-related endpoints
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Value("${cognito.domain}")
    private String cognitoDomain;

    @Value("${cognito.clientId}")
    private String clientId;

    @Value("${cognito.redirectUri}")
    private String redirectUri;

    /**
     * Endpoint to get the Cognito login URL
     * @return The Cognito login URL
     */
    @GetMapping("/login-url")
    public ResponseEntity<Map<String, String>> getLoginUrl() {
        String loginUrl = cognitoDomain + "/login?client_id=" + clientId + 
                          "&response_type=code&redirect_uri=" + redirectUri;
        
        Map<String, String> response = new HashMap<>();
        response.put("loginUrl", loginUrl);
        
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint to redirect to Cognito login
     * @return A redirect to the Cognito login page
     */
    @GetMapping("/login")
    public RedirectView login() {
        String loginUrl = cognitoDomain + "/login?client_id=" + clientId + 
                          "&response_type=code&redirect_uri=" + redirectUri;
        
        return new RedirectView(loginUrl);
    }

    /**
     * Endpoint to handle the callback from Cognito
     * @param code The authorization code from Cognito
     * @param error Any error that occurred during authentication
     * @return A response with the authentication result
     */
    @GetMapping("/callback")
    public ResponseEntity<Map<String, String>> callback(
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String error) {
        
        Map<String, String> response = new HashMap<>();
        
        if (error != null) {
            response.put("status", "error");
            response.put("message", "Authentication failed: " + error);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        
        if (code == null) {
            response.put("status", "error");
            response.put("message", "No authorization code provided");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        
        // In a real implementation, we would exchange the code for tokens
        // For now, we'll just return a success message
        response.put("status", "success");
        response.put("message", "Authentication successful");
        response.put("code", code);
        
        return ResponseEntity.ok(response);
    }
}
