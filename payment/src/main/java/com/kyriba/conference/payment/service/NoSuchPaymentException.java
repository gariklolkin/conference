package com.kyriba.conference.payment.service;

import java.util.NoSuchElementException;

public class NoSuchPaymentException extends NoSuchElementException {
    NoSuchPaymentException(String id,String type) {
        super(String.format("%s with type %s doesn't exist", type));
    }
}
