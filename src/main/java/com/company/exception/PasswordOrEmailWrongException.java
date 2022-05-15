package com.company.exception;

public class PasswordOrEmailWrongException extends RuntimeException{
    public PasswordOrEmailWrongException(String message) {
        super(message);
    }
}
