package com.kyriba.conference.management.domain;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;


//@Entity
@Getter
@ToString
@EqualsAndHashCode
public class Hall
{
  private Long id;
  private String name;
  private Integer places;


  public Hall(Long id)
  {
    this.id = id;
  }


  public Hall withName(String name)
  {
    this.name = name;
    return this;
  }


  public Hall withPlaces(int places)
  {
    this.places = places;
    return this;
  }
}
