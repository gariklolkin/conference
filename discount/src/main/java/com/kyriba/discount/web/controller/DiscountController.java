package com.kyriba.discount.web.controller;

import com.kyriba.discount.domain.DiscountType;
import com.kyriba.discount.domain.dto.DiscountDto;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

import static com.kyriba.discount.domain.DiscountType.STUDENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * @author Igor Lizura
 */
@RestController
@AllArgsConstructor
@RequestMapping("/v1/discount")
public class DiscountController {
    @ResponseBody
    @GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<DiscountDto>> getDiscounts() {
        List<DiscountDto> response = Arrays.asList(new DiscountDto(DiscountType.JUNIOR, 30),
                new DiscountDto(STUDENT, 50));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping(produces = APPLICATION_JSON_UTF8_VALUE, consumes = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<DiscountResponse> createDiscount(@RequestBody DiscountDto discount) {
        return new ResponseEntity<>(new DiscountResponse(STUDENT), HttpStatus.CREATED);
    }

    @ResponseBody
    @GetMapping(value = "/{type}", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<DiscountDto> getDiscount(@PathVariable String type) {
        return new ResponseEntity<>(new DiscountDto(STUDENT, 30), HttpStatus.OK);
    }

    @ResponseBody
    @PutMapping(value = "/{type}", produces = APPLICATION_JSON_UTF8_VALUE, consumes = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<DiscountResponse> updateDiscount(@RequestBody DiscountDto discount) {
        return new ResponseEntity<>(new DiscountResponse(STUDENT), HttpStatus.OK);
    }

    @ResponseBody
    @DeleteMapping(value = "/{type}", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> deleteDiscount(@PathVariable String type) {
        return ResponseEntity.noContent().build();
    }

    @Value
    private class DiscountResponse {
        DiscountType type;
    }
}
