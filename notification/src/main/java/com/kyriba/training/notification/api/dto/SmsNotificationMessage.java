package com.kyriba.training.notification.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(description="The body of a SMS message object")
public class SmsNotificationMessage
{
  @ApiModelProperty(required = true, value = "Message recipients list")
  @Size(min = 1)
  private List<Recipient> recipients;

  @ApiModelProperty(required = true, value = "Message text")
  @NotBlank
  private String body;
}
