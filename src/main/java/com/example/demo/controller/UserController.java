package com.example.demo.controller;

import com.example.demo.models.Request.Login;
import com.example.demo.models.ResponseBody;
import com.example.demo.models.User;
import com.example.demo.models.UserDetailsImpl;
import com.example.demo.services.TokenBlacklistService;
import com.example.demo.services.UserService;
import com.example.demo.utility.ErrorHandler;
import com.example.demo.utility.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.example.demo.security.AuthenticationFilter.HEADER_STRING;
import static com.example.demo.security.AuthenticationFilter.TOKEN_PREFIX;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final TokenBlacklistService tokenBlacklistService;

    public UserController(UserService userService, AuthenticationManager authenticationManager, JwtUtils jwtUtils, TokenBlacklistService tokenBlacklistService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.tokenBlacklistService = tokenBlacklistService;
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseBody<User>> authenticateUser(
            HttpServletRequest request,
            @RequestBody Login requestBody) {

        String userEmail = "";
        User userData = null;
        MultiValueMap<String, String> respHeader = new LinkedMultiValueMap<>();
        ResponseBody<User> apiResponse;


        try {
            if (requestBody.getEmail() == null) {

                Optional<User> optUsername = userService.findUserByUsername(requestBody.getUsername());

                if (!optUsername.isPresent()) {

                    apiResponse = new ResponseBody<>(HttpStatus.EXPECTATION_FAILED, null, "User not found with this username");
                    return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());

                }

                userEmail = optUsername.get().getEmail();
                userData = optUsername.get();

            } else {

                Optional<User> optUserByEmail = userService.findUserByEmail(requestBody.getEmail());
                if (!optUserByEmail.isPresent()) {

                    apiResponse = new ResponseBody<>(HttpStatus.EXPECTATION_FAILED, null, "User not found with this email");
                    return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());
                }
                userEmail = optUserByEmail.get().getEmail();
                userData = optUserByEmail.get();

            }

            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(userEmail, requestBody.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            respHeader.add(HttpHeaders.AUTHORIZATION, "Bearer " + jwtUtils.getJwtToken(userDetails));

            apiResponse = new ResponseBody<>(HttpStatus.OK, userData);
        } catch (Exception e) {
            apiResponse = ErrorHandler.handleError(e, HttpStatus.INTERNAL_SERVER_ERROR, null);
        }

        return new ResponseEntity<>(apiResponse, respHeader, apiResponse.getHttpStatus());
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(HttpServletRequest request) {
        ResponseBody<String> apiResponse;
        try {
            String accessToken = request.getHeader(HEADER_STRING);
            if (accessToken.startsWith("Bearer ")) {
                accessToken = accessToken.replace(TOKEN_PREFIX, "");
            }
            String email = jwtUtils.getUserNameFromJwtToken(accessToken);
            String currentJti = jwtUtils.getJti(accessToken);
            Optional<User> optUserByEmail = userService.findUserByEmail(email);

            List<String> blackListJti = tokenBlacklistService.getValueFromCache(optUserByEmail.get().getId());
            log.info("Checking blackListJti b4 : {}", blackListJti);
            if (blackListJti == null) {
                blackListJti = new ArrayList<>(Arrays.asList(currentJti));

            } else {
                blackListJti.add(currentJti);

            }
            List<String> updatedBlacklist = tokenBlacklistService.updateCacheValue(optUserByEmail.get().getId(), blackListJti);
            log.info("Checking blackListJti : {}", updatedBlacklist);
            apiResponse = new ResponseBody<>(HttpStatus.OK, "You've been signed out!");
        } catch (Exception e) {
            apiResponse = ErrorHandler.handleError(e, HttpStatus.INTERNAL_SERVER_ERROR, null);
        }

        return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());
    }


}
