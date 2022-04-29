package com.example.demo.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HttpReponse<T> {

    private T data;
    private String message;
    private int status;
    private String error;
    private String path;
    private HttpStatus httpStatus;
    private Date timestamp;


    public HttpReponse(HttpStatus httpStatus) {
        this.status = httpStatus.value();
        this.message = httpStatus.getReasonPhrase();

    }
    
    public HttpReponse(HttpStatus httpStatus, T data) {
        this.status = httpStatus.value();
        this.message = httpStatus.getReasonPhrase();
        this.data = data;
    }

    public HttpReponse(HttpStatus httpStatus, T data, String message) {
        this.status = httpStatus.value();
        this.data = data;
        this.message= message;
    }

}
