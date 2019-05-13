package com.kyriba.conference.domain;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;


//@Entity
@Getter
@ToString
@EqualsAndHashCode
public class Topic
{
  private Long id;
  private String title;
  private String author;
}
