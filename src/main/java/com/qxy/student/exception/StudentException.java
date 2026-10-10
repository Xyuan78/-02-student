package com.qxy.student.exception;

public class StudentException extends RuntimeException {
    //异常处理
    public StudentException() {
        super();
    }
    public StudentException(String message) {
        super(message);
    }
}
