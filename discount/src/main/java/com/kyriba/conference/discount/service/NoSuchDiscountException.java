package com.kyriba.conference.discount.service;

import java.util.NoSuchElementException;

/**
 * @author Igor Lizura
 */
public class NoSuchDiscountException extends NoSuchElementException {

    NoSuchDiscountException(String type) {
        super(String.format("Discount with type %s doesn't exist", type));
    }
}
