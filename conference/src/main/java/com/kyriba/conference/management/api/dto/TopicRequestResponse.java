package com.kyriba.conference.management.api.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@ApiModel(description = "Presentation topic model")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopicRequestResponse
{
  @ApiModelProperty(value = "Topic title")
  private String title;

  @ApiModelProperty(value = "Author")
  private String author;
}
