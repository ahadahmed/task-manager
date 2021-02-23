package com.cardinity.taskmanager.controllers.rest;

import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ahadahmed
 * @see <a href="#"> see this</a>
 */
public class ErrorResponse {
    private HttpStatus status;
    private String error;
    private List<ValidationError> errors;

    public ErrorResponse(HttpStatus status, String error) {
        this.status = status;
        this.error = error;
        this.errors = new ArrayList<>();
    }


    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<ValidationError> getErrors() {
        return errors;
    }

    public void setErrors(List<ValidationError> errors) {
        this.errors = errors;
    }

    public void addValidationError(String field, String message, String objectName, Object rejectedValue, String code) {

        this.errors.add(new ValidationError(field, message, objectName, rejectedValue, code));
    }

    private class ValidationError {
        private String field;
        private String message;
        private String objectName;
        private Object rejectedValue;
        private String code;

        public ValidationError(String field, String message, String objectName, Object rejectedValue, String code) {
            this.field = field;
            this.message = message;
            this.objectName = objectName;
            this.rejectedValue = rejectedValue;
            this.code = code;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getObjectName() {
            return objectName;
        }

        public void setObjectName(String objectName) {
            this.objectName = objectName;
        }

        public Object getRejectedValue() {
            return rejectedValue;
        }

        public void setRejectedValue(Object rejectedValue) {
            this.rejectedValue = rejectedValue;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}
