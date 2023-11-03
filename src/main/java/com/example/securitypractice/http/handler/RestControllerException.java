package com.example.securitypractice.http.handler;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
@RestControllerAdvice(basePackages = "com.example.securityPractice.http.rest")
public class RestControllerException extends ResponseEntityExceptionHandler {
}
