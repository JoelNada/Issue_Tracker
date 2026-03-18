package com.joel.issue_tracker.exceptions.customExceptions;

import org.springframework.security.core.AuthenticationException;

public class UnAuthorizedException extends AuthenticationException {
    public UnAuthorizedException(String s) {
        super(s);
    }
}
