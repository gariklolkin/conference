package com.kyriba.submittal.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;


/**
 * @author M-ABL
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
public class Registration
{
  @Id
  @Column(name = "speaker_id")
  private Long id;

  @MapsId
  @OneToOne(targetEntity = Speaker.class)
  @JoinColumn(name = "speaker_id")
  private Speaker speaker;
  @Column(name = "confirmation_key")
  private String confirmationKey;


  public Registration(final Speaker speaker, final String confirmationKey)
  {
    this.speaker = speaker;
    this.confirmationKey = confirmationKey;
  }
}
