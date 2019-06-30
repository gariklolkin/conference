package com.kyriba.conference.sponsorship.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * @author M-ASL
 * @since v1.0
 */
@Entity
@Table(name = "plan")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Plan
{
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_identity_id")
  @SequenceGenerator(name = "seq_identity_id", sequenceName = "seq_identity_id", allocationSize = 1)
  @Id
  @Column(name = "id", updatable = false, nullable = false)
  private Long id;

  @Column(name = "category", nullable = false)
  @Enumerated(EnumType.STRING)
  private PlanCategory category;

  @ManyToOne(targetEntity = Sponsor.class)
  @JoinColumn(name = "sponsor_id")
  private Long sponsorId;
}
