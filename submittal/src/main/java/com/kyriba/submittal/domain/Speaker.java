package com.kyriba.submittal.domain;

import com.kyriba.submittal.domain.dto.JobInfo;
import com.kyriba.submittal.domain.dto.PersonalInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;


/**
 * @author M-ABL
 */
@NoArgsConstructor
@Getter
@Setter
public class Speaker
{
  private Long id;
  @NotBlank
  @Email
  @Size(max = 100)
  private String email;
  @NotNull
  private SpeakerStatus status;
  private PersonalInfo personalInfo;
  private JobInfo jobInfo;
  private List<Topic> topics;
  @NotBlank
  private String password;


  public void setPassword(final String password)
  {
    // TODO: make a hash
    this.password = password;
  }


  public void activate()
  {
    setStatus(SpeakerStatus.ACTIVE);
  }
}
