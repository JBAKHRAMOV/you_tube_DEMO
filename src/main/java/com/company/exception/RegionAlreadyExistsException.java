package com.company.exception;

public class RegionAlreadyExistsException extends RuntimeException{
    public RegionAlreadyExistsException(String message) {
        super(message);
    }
}
