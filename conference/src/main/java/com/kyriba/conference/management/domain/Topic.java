package com.kyriba.conference.management.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;


@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Topic
{
  private String title;
  private String author;
}
