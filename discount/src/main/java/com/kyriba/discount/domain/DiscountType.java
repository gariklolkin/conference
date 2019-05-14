package com.kyriba.discount.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * @author Igor Lizura
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public enum DiscountType {
    STUDENT,
    JUNIOR
}
