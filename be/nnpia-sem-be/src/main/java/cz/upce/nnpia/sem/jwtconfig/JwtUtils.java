package cz.upce.nnpia.sem.jwtconfig;

import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {
    @Value("${secret}")
    private String jwtSecret;

    @Value("${expire}")
    private int jwtExpirationMs;

    public String generateJwtToken(Authentication authentication) {

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            System.out.println("Invalid JWT signature: {}");
        } catch (MalformedJwtException e) {
            System.out.println("Invalid JWT token: {}");
        } catch (ExpiredJwtException e) {
            System.out.println("JWT token is expired: {}");
        } catch (UnsupportedJwtException e) {
            System.out.println("JWT token is unsupported: {}");
        } catch (IllegalArgumentException e) {
            System.out.println("JWT claims string is empty: {}");
        }

        return false;
    }
}
