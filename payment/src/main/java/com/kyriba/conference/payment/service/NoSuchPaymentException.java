package com.kyriba.conference.payment.service;

public class NoSuchPaymentException extends RuntimeException {
    public NoSuchPaymentException(String id,String type) {
        super(String.format("%s with type %s doesn't exist", type));
    }
}
