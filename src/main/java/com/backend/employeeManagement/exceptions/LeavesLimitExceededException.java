package com.backend.employeeManagement.exceptions;

public class LeavesLimitExceededException extends RuntimeException{

    public LeavesLimitExceededException (String message){
        super(message);
    }
}
