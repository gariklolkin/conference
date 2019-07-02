package com.kyriba.conference.sponsorship.domain;

import com.kyriba.conference.sponsorship.domain.dto.PlanDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;


/**
 * @author M-ASL
 * @since v1.0
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Plan
{
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_identity_id")
  @SequenceGenerator(name = "seq_identity_id", sequenceName = "seq_identity_id", allocationSize = 1)
  @Id
  private Long id;

  @Enumerated(EnumType.STRING)
  private PlanCategory category;

  @ManyToOne(targetEntity = Sponsor.class)
  @JoinColumn(name = "sponsor_id")
  private Sponsor sponsor;


  public PlanDto toDto()
  {
    return new PlanDto(id, category, sponsor == null ? null : sponsor.getId());
  }
}
