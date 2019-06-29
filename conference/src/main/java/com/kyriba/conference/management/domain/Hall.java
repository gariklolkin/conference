package com.kyriba.conference.management.domain;


import com.kyriba.conference.management.api.dto.HallRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
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


  public Hall(HallRequest hallRequest)
  {
    update(hallRequest);
  }


  public Hall update(HallRequest hallRequest)
  {
    name = hallRequest.getName();
    places = hallRequest.getPlaces();
    return this;
  }
}
