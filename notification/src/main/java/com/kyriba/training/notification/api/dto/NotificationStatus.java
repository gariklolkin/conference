package com.kyriba.training.notification.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(description="The status of a notification message")
public class NotificationStatus
{
  @ApiModelProperty(value = "The message ID")
  private String messageId;

  @ApiModelProperty(value = "The message status")
  private MessageStatus messageStatus;

  @ApiModelProperty(value = "The message type")
  private MessageType messageType;
}
