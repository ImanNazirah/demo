package com.example.demo.utility;

import com.example.demo.models.UserDetailsImpl;
import com.example.demo.services.TokenBlacklistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationMs}")
    private int jwtExpirationMs;

    @Value("${app.jwtCookieName}")
    private String jwtCookie;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    private final TokenBlacklistService tokenBlacklistService;

    public JwtUtils(TokenBlacklistService tokenBlacklistService) {
        this.tokenBlacklistService = tokenBlacklistService;
    }

    public String getHeaderBearer(HttpServletRequest request) {

        String bearerHeaderValue = request.getHeader("Authorization");

        if(bearerHeaderValue != null){
            return bearerHeaderValue;
        } else{
            return null;
        }

    }

    public String getJwtToken(UserDetailsImpl userPrincipal){

        String jwt = generateTokenFromUsername(userPrincipal.getUsername(), UUID.randomUUID().toString());
        return jwt;

    }


    public String refreshToken(String authToken){

        String refreshToken = generateTokenFromUsername(getUserNameFromJwtToken(authToken),getJti(authToken),getIssueDate(authToken));
        return refreshToken;

    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public String getJti(String token){
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getId();
    }

    public Date getIssueDate(String token){
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getIssuedAt();

    }

    public Date getExpiryDate(String token){
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getExpiration();

    }

    public boolean validateJwtToken(String authToken, String reqURI) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            logger.info("REQUESTURI : {} Valid JWT token",reqURI);
            return true;
        } catch (SignatureException e) {
            logger.error("REQUESTURI : {} Invalid JWT signature: {}", reqURI, e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("REQUESTURI : {} Invalid JWT token: {}", reqURI, e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("REQUESTURI : {} JWT token is expired: {}", reqURI, e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("REQUESTURI : {} JWT token is unsupported: {}", reqURI, e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("REQUESTURI : {} JWT claims string is empty: {}", reqURI, e.getMessage());
        }

        return false;
    }

    public String generateTokenFromUsername(String username,String jti) {
        return Jwts.builder()
                .setSubject(username)
                .setId(jti)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String generateTokenFromUsername(String username,String jti, Date issueAt) {
        return Jwts.builder()
                .setSubject(username)
                .setId(jti)
                .setIssuedAt(issueAt)
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public boolean isTokenBlacklisted(String token , String userId) {
        List<String> blacklistJti = tokenBlacklistService.getValueFromCache(userId);
        logger.info("blacklistJti : {}",blacklistJti);
        String currentJti = getJti(token);
        logger.info("currentJti : {}",currentJti);

        if(blacklistJti == null){
            return false;
        }

        return blacklistJti.contains(currentJti);

    }


}
