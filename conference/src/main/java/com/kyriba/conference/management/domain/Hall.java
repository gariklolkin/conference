package com.kyriba.conference.management.domain;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
@Data
@NoArgsConstructor
public class Hall
{
  @Id
  @GeneratedValue
  private Long id;
  @Column(length = 100, unique = true, nullable = false)
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
