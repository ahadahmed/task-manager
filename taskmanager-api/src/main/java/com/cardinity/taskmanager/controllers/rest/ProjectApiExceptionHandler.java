package com.cardinity.taskmanager.controllers.rest;

import com.cardinity.taskmanager.service.InvalidTaskException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.util.NoSuchElementException;

/**
 * @author ahadahmed
 * @see <a href="#"> see this</a>
 */
@ControllerAdvice("com.cardinity.taskmanager.controllers.rest")
public class ProjectApiExceptionHandler extends ResponseEntityExceptionHandler {


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        final String msg = HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase();
        ErrorResponse response = new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY, msg);
        for (FieldError error : ex.getFieldErrors()) {
            response.addValidationError(error.getField(), error.getDefaultMessage(), error.getObjectName(), error.getRejectedValue(), error.getCode());
        }
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(value = {NoSuchElementException.class})
    public ResponseEntity<Object> handleProjectNotFound(WebRequest request, Exception ex) {
        final String msg = ex.getMessage();
        ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND, msg);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

    }


    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<Object> handleValidationException(Exception ex) {
        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex, HttpServletResponse res) {
        final String msg = ex.getMessage();
        ErrorResponse response = new ErrorResponse(HttpStatus.FORBIDDEN, msg);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException ex, HttpServletResponse res) {
        final String msg = ex.getMessage();
        ErrorResponse response = new ErrorResponse(HttpStatus.FORBIDDEN, msg);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler(InvalidTaskException.class)
    public ResponseEntity<ErrorResponse> handleInvalidTaskException(InvalidTaskException ex, HttpServletResponse res) {
        final String msg = ex.getMessage();
        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST, msg);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
