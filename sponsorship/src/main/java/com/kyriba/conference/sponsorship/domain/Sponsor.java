package com.kyriba.conference.sponsorship.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * @author M-ASL
 * @since v1.0
 */
@Entity
@Table(name = "sponsor")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Sponsor
{
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_identity_id")
  @SequenceGenerator(name = "seq_identity_id", sequenceName = "seq_identity_id", allocationSize = 1)
  @Id
  @Column(name = "id", updatable = false, nullable = false)
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "email", unique = true)
  private String email;
}
