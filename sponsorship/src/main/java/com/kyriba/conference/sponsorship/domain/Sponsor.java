package com.kyriba.conference.sponsorship.domain;

import com.kyriba.conference.sponsorship.domain.dto.SponsorDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Arrays;
import java.util.HashSet;


/**
 * @author M-ASL
 * @since v1.0
 */
@Entity
@Table(name = "sponsor")
@Getter
@Setter
@NoArgsConstructor
public class Sponsor
{
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_identity_id")
  @SequenceGenerator(name = "seq_identity_id", sequenceName = "seq_identity_id", allocationSize = 1)
  @Id
  private Long id;
  private String name;
  private String email;


  public SponsorDto toDto()
  {
    return new SponsorDto(id, name, email);
  }


  public EmailMessage toEmailMessage()
  {
    EmailMessage emailMessage = new EmailMessage(
        Arrays.asList(Recipient.builder().name(name).address(email).build()),
        String.format("Sponsor %s has bean registered", name),
        "Sponsor registration notification"
    );
    emailMessage.setEmailMessageOptions(new HashSet<>(Arrays.asList(EmailMessage.Options.HIGH_PRIORITY)));
    return emailMessage;
  }
}
