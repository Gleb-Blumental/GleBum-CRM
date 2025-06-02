package gleb.blum.examensarbete.CognitoAuth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;

//Validate JWT Tokens from Cognito
@Service
public class TokenValidationService {

    @Value("${cognito.publicKey}")
    private String cognitoPublicKey;

    public Claims validateToken(String token) {
        try {
            Key key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(cognitoPublicKey));
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            // Log the exception
            System.err.println("Error validating token: " + e.getMessage());
            return null;
        }
    }
}
