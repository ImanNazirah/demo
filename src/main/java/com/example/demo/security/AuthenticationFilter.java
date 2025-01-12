package com.example.demo.security;

import com.example.demo.models.CustomResponseWrapper;
import com.example.demo.services.UserDetailsServiceImpl;
import com.example.demo.services.UserService;
import com.example.demo.utility.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    public static final String HEADER_STRING = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {

            final String authHeader = request.getHeader(HEADER_STRING);

            String accessToken = null;

            if (authHeader != null){

                if (authHeader.startsWith("Bearer ")) {
                    accessToken = authHeader.replace("Bearer ", "");
                }
            }

            String username = jwtUtils.getUserNameFromJwtToken(accessToken);
            String userId = userService.findUserByEmail(username).get().getId();
            if(accessToken != null && jwtUtils.validateJwtToken(accessToken, request.getRequestURI())  && !jwtUtils.isTokenBlacklisted(accessToken,userId)){

                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);


            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }

        filterChain.doFilter(request, response);

        // Wrap the original response in our custom wrapper
        CustomResponseWrapper customResponse = new CustomResponseWrapper((HttpServletResponse) response);

        byte[] responseBody = customResponse.getResponseBody();
        String responseBodyStr = "";
        if (responseBody.length > 0) {
            responseBodyStr = new String(responseBody, response.getCharacterEncoding());
            logger.info("Captured Response Body: " + responseBodyStr);

        }
        // Log the request URI
        log.info("{} REQUESTURI: {} , HTTPSTATUSCODE: {} , RESPONSEBODY: {}", request.getMethod(), request.getRequestURI(), String.valueOf(response.getStatus()), responseBodyStr);


    }

    private String parseJwt(HttpServletRequest request) {
        // String jwt = jwtUtils.getJwtFromCookies(request);//for cookies implementation
        String jwt = jwtUtils.getHeaderBearer(request);
        return jwt;
    }
}

