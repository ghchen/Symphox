package com.symphox.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ModelViolatedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ModelViolatedException() {
        super("ModelViolated");
    }

    public ModelViolatedException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ModelViolatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ModelViolatedException(String message) {
        super(message);
    }

    public ModelViolatedException(Throwable cause) {
        super(cause);
    }

}
