package com.example.demo.utility;

import com.example.demo.models.User;
import com.example.demo.models.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import io.jsonwebtoken.*;
import org.springframework.http.ResponseCookie;
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

    public String getHeaderBearer(HttpServletRequest request) {

        String bearerHeaderValue = request.getHeader("Authorization");

        if(bearerHeaderValue != null){
            return bearerHeaderValue;
        } else{
            return null;
        }

    }

    public String getJwtToken(UserDetailsImpl userPrincipal){

        String jwt = generateTokenFromUsername(userPrincipal.getUsername(),userPrincipal.getId());
        return jwt;

    }

    public String getJwtToken(User user){

        String jwt = generateTokenFromUsername(user.getEmail(),user.getId());
        return jwt;

    }


    public String refreshToken(String authToken){

        String refreshToken = generateTokenFromUsername(getUserNameFromJwtToken(authToken),getUserId(authToken),getIssueDate(authToken));
        return refreshToken;

    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public String getUserId(String token){
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

    public ResponseCookie generateJwtCookie(UserDetailsImpl userPrincipal) {

        String jwt = getJwtToken(userPrincipal);
        ResponseCookie cookie = ResponseCookie.from(jwtCookie, jwt).path(contextPath).maxAge(24 * 60 * 60).httpOnly(true).build();
        return cookie;

    }
    public ResponseCookie getCleanJwtCookie() {
        ResponseCookie cookie = ResponseCookie.from(jwtCookie, null).path(contextPath).build();
        return cookie;
    }

}
