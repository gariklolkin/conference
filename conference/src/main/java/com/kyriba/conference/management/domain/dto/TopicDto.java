package com.kyriba.conference.management.domain.dto;


import com.kyriba.conference.management.domain.Topic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


@ApiModel(description = "Presentation topic model")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopicDto
{
  @ApiModelProperty(value = "Topic title")
  @NotBlank
  private String title;

  @ApiModelProperty(value = "Author")
  @NotBlank
  private String author;


  public TopicDto(Topic topic)
  {
    title = topic.getTitle();
    author = topic.getAuthor();
  }
}
