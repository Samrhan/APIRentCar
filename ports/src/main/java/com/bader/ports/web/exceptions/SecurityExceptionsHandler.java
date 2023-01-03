package com.bader.ports.web.exceptions;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@ControllerAdvice
public class SecurityExceptionsHandler {

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String, Object>> handleAuthenticationException(AuthenticationException ex,
                                                                             HttpServletRequest request,
                                                                             HttpServletResponse response,
                                                                             WebRequest webRequest) {
        return this.getErrorResponse(HttpStatus.UNAUTHORIZED, webRequest, request, response, ex);
    }

    private ResponseEntity<Map<String, Object>> getErrorResponse(HttpStatus status,
                                                                 WebRequest webRequest,
                                                                 HttpServletRequest request,
                                                                 HttpServletResponse response,
                                                                 Exception ex) {
        DefaultErrorAttributes errorAttributes = new DefaultErrorAttributes();
        errorAttributes.resolveException(request, response, null, ex);

        return ResponseEntity
                .status(status)
                .body(errorAttributes.getErrorAttributes(webRequest, ErrorAttributeOptions.of(ErrorAttributeOptions.Include.MESSAGE)));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleAccessDeniedException(AccessDeniedException ex,
                                                                           HttpServletRequest request,
                                                                           HttpServletResponse response,
                                                                           WebRequest webRequest) {

        return this.getErrorResponse(HttpStatus.FORBIDDEN, webRequest, request, response, ex);
    }
}
