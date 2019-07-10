package com.kyriba.submittal.domain.dto;

import lombok.Value;

import java.util.List;


/**
 * @author M-ABL
 */
@Value
public class CreatedTopicsDto
{
  private List<Long> topics;
}
