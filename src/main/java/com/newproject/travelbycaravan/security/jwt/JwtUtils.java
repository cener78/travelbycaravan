package com.newproject.travelbycaravan.security.jwt;

import com.newproject.travelbycaravan.security.service.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

    private static final Logger logger= LoggerFactory.getLogger(JwtUtils.class);

    @Value("${backendapi.app.jwtSecret}")
    private String jwtSecret;


    @Value("${backendapi.app.jwtExpirationMs}")
    private Long jwtExpirationMs;


    public String generateJwtToken(Authentication authentication){
        UserDetailsImpl userDetails= (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(""+ (userDetails.getId()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date().getTime()+jwtExpirationMs)))
                .signWith(SignatureAlgorithm.HS512,jwtSecret).compact();
    }

    public Long getIdFromJwtToken(String token){
        return Long.parseLong(Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject());
    }

    public boolean validateJwtToken(String authToken){
        try{
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        }
        catch (SecurityException e){
            logger.error("invalid JWT signature: {}",e.getMessage());

        }
        catch (MalformedJwtException e){
            logger.error("invalid JWT token: {}",e.getMessage());

        }
        catch (ExpiredJwtException e){
            logger.error(" JWT is expired: {}",e.getMessage());

        }
        catch (UnsupportedJwtException e){
            logger.error(" JWT token is unsupported: {}",e.getMessage());

        }
        catch (IllegalArgumentException e){
            logger.error(" JWT claim string is empty: {}",e.getMessage());

        }
        return false;


    }







}
