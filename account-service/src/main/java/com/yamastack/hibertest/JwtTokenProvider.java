package com.yamastack.hibertest;

import com.yamastack.hibertest.entity.User;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

/**
 * Create JWT Token to Retrieve User ID from Token
 */
@Component
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtKey}")
    private String jwtKey;

    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;

    public String generateToken(User user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);
        logger.debug("JwtToken Provider, jwtExpirationInMs:" + jwtExpirationInMs);
        logger.debug("JwtToken Provider, jwtKey:" + jwtKey);
        logger.debug("JwtToken Provider, jwtSecret:" + jwtSecret);

        logger.debug("JwtToken Provider, userID:" + user.getId());

        return Jwts.builder()
                .setSubject(user.getId())
                .setHeaderParam("typ", "JWT")
                .claim("id", user.getId())
                .claim("email", user.getEmail())
                .claim("phone", user.getPhone())
                .claim("firstName", user.getFirstName())
                .claim("middleName", user.getMiddleName())
                .claim("lastName", user.getLastName())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .setIssuer(jwtKey)
                .compact();
    }

    public User getUserInfoFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        if (claims.getIssuer().equals(jwtKey)) {
            User user= new User();
            user.setId(claims.get("id").toString());
            user.setEmail(claims.get("email").toString());
            user.setPhone(claims.get("phone").toString());
            user.setFirstName(claims.get("firstName").toString());
            user.setMiddleName(claims.get("middleName").toString());
            user.setLastName(claims.get("lastName").toString());
    
            return user;
        } else {
            return null;
        }
        
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }
        return false;
    }
}
