package gleb.blum.examensarbete.CognitoAuth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;

//Validate JWT Tokens from Cognito
@Service
public class TokenValidationService {

    private final String cognitoPublicKey = "public_key_cognito";

    public Claims validateToken(String token) {
        Key key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(cognitoPublicKey));
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}