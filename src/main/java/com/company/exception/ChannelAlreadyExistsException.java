package com.company.exception;

public class ChannelAlreadyExistsException extends RuntimeException{
    public ChannelAlreadyExistsException(String message) {
        super(message);
    }
}
