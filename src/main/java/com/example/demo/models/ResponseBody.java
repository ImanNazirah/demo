package com.example.demo.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import org.springframework.http.HttpStatus;



@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseBody<T> {

    private T data;
    private String message;
    private int status;
    @JsonIgnore
    private HttpStatus httpStatus;

    public ResponseBody(HttpStatus httpStatus) {
        this.status = httpStatus.value();
        this.message = httpStatus.getReasonPhrase();
        this.httpStatus = httpStatus;
    }
    
    public ResponseBody(HttpStatus httpStatus, T data) {
        this.status = httpStatus.value();
        this.message = httpStatus.getReasonPhrase();
        this.data = data;
        this.httpStatus = httpStatus;
    }

    public ResponseBody(HttpStatus httpStatus, T data, String message) {
        this.status = httpStatus.value();
        this.data = data;
        this.message= message;
        this.httpStatus = httpStatus;
    }

}
