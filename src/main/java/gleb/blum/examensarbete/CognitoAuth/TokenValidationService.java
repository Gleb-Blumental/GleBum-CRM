package gleb.blum.examensarbete.CognitoAuth;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.jwk.source.RemoteJWKSet;
import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.jose.proc.JWSKeySelector;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Service for validating JWT tokens from AWS Cognito
 */
@Service
public class TokenValidationService {
    private static final Logger logger = Logger.getLogger(TokenValidationService.class.getName());

    @Value("${security.oauth2.client.provider.cognito.issuerUri}")
    private String issuerUri;

    @Value("${cognito.jwk.url}")
    private String jwkUrl;

    @Value("${cognito.clientId}")
    private String clientId;

    /**
     * Validates a JWT token from Cognito
     * 
     * @param token The JWT token to validate
     * @return The claims from the token if valid, null otherwise
     */
    public JWTClaimsSet validateToken(String token) {
        try {
            // Create a JWT processor for the access tokens
            ConfigurableJWTProcessor<SecurityContext> jwtProcessor = new DefaultJWTProcessor<>();

            // Set up the JWK source
            JWKSource<SecurityContext> jwkSource = new RemoteJWKSet<>(new URL(jwkUrl));
            
            // The expected JWS algorithm of the access tokens
            JWSAlgorithm expectedJWSAlg = JWSAlgorithm.RS256;
            
            // Configure the JWT processor with a key selector to feed matching public
            JWSKeySelector<SecurityContext> keySelector = new JWSVerificationKeySelector<>(expectedJWSAlg, jwkSource);
            jwtProcessor.setJWSKeySelector(keySelector);

            // Process the token
            SecurityContext ctx = null; // optional context parameter, not used here
            JWTClaimsSet claimsSet = jwtProcessor.process(token, ctx);

            // Validate the token further
            validateTokenClaims(claimsSet);
            
            return claimsSet;
        } catch (MalformedURLException | ParseException | BadJOSEException | JOSEException e) {
            logger.log(Level.WARNING, "Error validating token: " + e.getMessage(), e);
            return null;
        }
    }

    /**
     * Validates the claims in the JWT token
     * 
     * @param claimsSet The claims to validate
     * @throws BadJOSEException If the claims are invalid
     */
    private void validateTokenClaims(JWTClaimsSet claimsSet) throws BadJOSEException {
        // Verify the token was issued by the expected issuer
        if (!claimsSet.getIssuer().equals(issuerUri)) {
            throw new BadJOSEException("Invalid token issuer");
        }

        // Verify the token is intended for our application
        if (!claimsSet.getAudience().contains(clientId)) {
            throw new BadJOSEException("Invalid token audience");
        }

        // Verify the token is not expired
        if (claimsSet.getExpirationTime().getTime() < System.currentTimeMillis()) {
            throw new BadJOSEException("Token is expired");
        }
    }
}
