package com.kyriba.conference.discount.service;

import java.util.NoSuchElementException;

/**
 * @author Igor Lizura
 */
public class NoSuchDiscountException extends NoSuchElementException {

    private final String type;

    public NoSuchDiscountException(String type) {
        this.type = type;
    }

    @Override
    public String getMessage() {
        String message = super.getMessage();
        return message == null ? String.format("Discount with type %s doesn't exist", type) : message;
    }
}
