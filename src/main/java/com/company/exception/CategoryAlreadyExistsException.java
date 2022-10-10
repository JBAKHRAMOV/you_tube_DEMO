package com.company.exception;

public class CategoryAlreadyExistsException extends RuntimeException {
    public CategoryAlreadyExistsException(String massage) {
        super(massage);
    }
}
