package com.app.security.exceptions;

import org.springframework.security.core.AuthenticationException;

public class AppAuthenticationException extends AuthenticationException {
    public AppAuthenticationException(String msg, Throwable t) {
        super(msg, t);
    }
}