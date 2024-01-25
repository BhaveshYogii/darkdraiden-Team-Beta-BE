package com.backend.employeeManagement.exceptions;

public class PhoneNumberAlreadyExistsException extends RuntimeException {

    public PhoneNumberAlreadyExistsException(String message) {
        super(message);
    }
}