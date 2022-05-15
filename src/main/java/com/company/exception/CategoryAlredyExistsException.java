package com.company.exception;

public class CategoryAlredyExistsException extends RuntimeException{
    public CategoryAlredyExistsException(String message) {
        super(message);
    }
}
