package com.company.exception;

public class ChannelAlredyExistsException extends RuntimeException{
    public ChannelAlredyExistsException(String message) {
        super(message);
    }
}
