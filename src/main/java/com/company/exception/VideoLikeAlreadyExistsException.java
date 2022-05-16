package com.company.exception;

public class VideoLikeAlreadyExistsException extends RuntimeException {
    public VideoLikeAlreadyExistsException(String message) {
        super(message);
    }
}
