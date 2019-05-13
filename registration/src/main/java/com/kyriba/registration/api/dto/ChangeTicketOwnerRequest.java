package com.kyriba.registration.api.dto;


import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class ChangeTicketOwnerRequest
{
  private String ticketOwner;
}
