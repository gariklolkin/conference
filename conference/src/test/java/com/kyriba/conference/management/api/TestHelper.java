package com.kyriba.conference.management.api;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class TestHelper
{
  private static final DateTimeFormatter HHmm = DateTimeFormatter.ofPattern("HH:mm");

  public static String getPresentationJson(long hallId, String topicTitle, String topicAuthor, LocalTime startTime,
                                     LocalTime endTime)
  {
    return "{\n" +
        "  \"hall\": " + hallId + ",\n" +
        "  \"topic\": {\n" +
        "    \"title\": \"" + topicTitle + "\",\n" +
        "    \"author\" : \"" + topicAuthor + "\"\n" +
        "  },\n" +
        "  \"startTime\": \"" + startTime.format(HHmm) + "\",\n" +
        "  \"endTime\" : \"" + endTime.format(HHmm) + "\"\n" +
        "}";
  }
}
