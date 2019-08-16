package com.kyriba.conference.management.domain;


import com.kyriba.conference.management.domain.dto.HallRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import static com.google.common.base.Preconditions.checkNotNull;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Hall
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private Integer places;


  public Hall(Long id)
  {
    this.id = id;
  }


  public Hall(HallRequest hallRequest)
  {
    checkNotNull(hallRequest);

    update(hallRequest);
  }


  public void update(HallRequest hallRequest)
  {
    checkNotNull(hallRequest);

    name = hallRequest.getName();
    places = hallRequest.getPlaces();
  }
}
