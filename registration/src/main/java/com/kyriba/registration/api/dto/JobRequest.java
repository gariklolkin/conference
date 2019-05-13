package com.kyriba.registration.api.dto;


import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class JobRequest
{
  private String company;
  private String position;
  private String city;
}
