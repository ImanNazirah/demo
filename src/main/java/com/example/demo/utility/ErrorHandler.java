package com.example.demo.utility;

import com.example.demo.models.ResponseBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
public class ErrorHandler {

    public static <T> ResponseBody<T> handleError(Exception e, HttpStatus status, String message) {
        log.error("Error :: {}", e.getMessage(), e);
        return new ResponseBody<>(status, null, message != null ? message : status.getReasonPhrase());
    }
}
