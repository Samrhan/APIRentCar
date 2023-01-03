package com.bader.domain.todo.exception;

public class OrderConflictException extends RuntimeException {

    public OrderConflictException() {
        super("Order conflict");
    }
}
