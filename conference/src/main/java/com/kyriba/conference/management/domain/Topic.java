package com.kyriba.conference.management.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Topic
{
  @Column(unique = true)
  private String title;
  private String author;
}
