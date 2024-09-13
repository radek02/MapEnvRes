package com.example.crudApp.exceptions;

public class ResourceAlreadyReservedException extends Exception {
    public ResourceAlreadyReservedException(String message) {
        super(message);
    }
}
