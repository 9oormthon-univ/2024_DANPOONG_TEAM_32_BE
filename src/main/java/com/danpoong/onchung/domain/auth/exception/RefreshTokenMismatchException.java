package com.danpoong.onchung.domain.auth.exception;

public class RefreshTokenMismatchException extends RuntimeException {
    public RefreshTokenMismatchException(String message) {
        super(message);
    }
}
