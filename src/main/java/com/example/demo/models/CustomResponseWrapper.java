package com.example.demo.models;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

public class CustomResponseWrapper extends HttpServletResponseWrapper {
    private ByteArrayOutputStream capture;
    private PrintWriter writer;


    public CustomResponseWrapper(HttpServletResponse response) {
        super(response);
        this.capture = new ByteArrayOutputStream();
        this.writer = new PrintWriter(capture);
    }

    @Override
    public PrintWriter getWriter() {
        return this.writer;
    }

    public byte[] getResponseBody() {
        this.writer.flush();
        return capture.toByteArray();
    }

}
