package com.kyriba.conference.notification.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(description="The body of a email message object")
public class EmailNotificationMessage
{
  @ApiModelProperty(required = true, value = "Message recipients list")
  @Size(min = 1)
  private List<Recipient> recipients;

  @ApiModelProperty(required = true, value = "Message text")
  @NotBlank
  private String body;

  @ApiModelProperty(required = true, value = "The subject of a email message")
  @NotBlank
  private String subject;

  @ApiModelProperty(value = "Email options")
  private Set<EmailMessageOptions> emailMessageOptions;
}
