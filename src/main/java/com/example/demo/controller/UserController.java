package com.example.demo.controller;

import com.example.demo.models.Request.Login;
import com.example.demo.models.ResponseBody;
import com.example.demo.models.User;
import com.example.demo.models.UserDetailsImpl;
import com.example.demo.services.UserService;
import com.example.demo.utility.JwtUtils;
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
import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public UserController(UserService userService, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseBody<User>> authenticateUser(
            HttpServletRequest request,
            @RequestBody Login requestBody) {

        String userEmail ="";
        User userData = null;

        if(requestBody.getEmail() == null){

            Optional<User> optUsername = userService.findUserByUsername(requestBody.getUsername());

            if(!optUsername.isPresent()){

                ResponseBody<User> apiResponse = new ResponseBody<>(
                        HttpStatus.EXPECTATION_FAILED, null, "User not found with this username"
                );
                return new ResponseEntity<>(apiResponse, HttpStatus.EXPECTATION_FAILED);

            }

            userEmail = optUsername.get().getEmail();
            userData = optUsername.get();

        } else{

            Optional<User> optUserByEmail = userService.findUserByEmail(requestBody.getEmail());
            if(!optUserByEmail.isPresent()){

                ResponseBody<User> apiResponse = new ResponseBody<>(
                        HttpStatus.EXPECTATION_FAILED, null, "User not found with this email"
                );
                return new ResponseEntity<>(apiResponse, HttpStatus.EXPECTATION_FAILED);
            }
            userEmail = optUserByEmail.get().getEmail();
            userData = optUserByEmail.get();

        }

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(userEmail, requestBody.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

//        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

        MultiValueMap<String, String> respHeader = new LinkedMultiValueMap<>();
        respHeader.add(HttpHeaders.AUTHORIZATION, "Bearer "+jwtUtils.getJwtToken(userDetails));
//        respHeader.add(HttpHeaders.SET_COOKIE, jwtCookie.toString());


        ResponseBody<User> respBody = new ResponseBody<>(
                HttpStatus.OK, userData
        );
        return new ResponseEntity<>(respBody, respHeader, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser() {
        ResponseBody<String> apiResponse = new ResponseBody<>(
                HttpStatus.OK, "You've been signed out!"
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);

//        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
//                .body("You've been signed out!");

    }


    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(  HttpServletRequest request,
                                            @RequestBody HashMap<String,String> jwt) {

        //generate new token
        String jwtToken = jwtUtils.refreshToken(jwt.get("jwt"));

        ResponseBody<User> apiResponse = new ResponseBody<>(
                HttpStatus.OK
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);

//        return ResponseEntity.ok()
//                .header("Authorization", "Bearer "+jwtToken)
//                .body(jwtBody);
    }

}
