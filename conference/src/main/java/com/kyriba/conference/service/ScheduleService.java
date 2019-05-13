package com.kyriba.conference.service;

import com.kyriba.conference.api.dto.PresentationRequest;
import com.kyriba.conference.domain.Presentation;

import java.util.List;


public interface ScheduleService
{
  List<Presentation> getSchedule();

  Long addPresentation(PresentationRequest presentation);

  Presentation getPresentation();

  Long updatePresentation(Long id, PresentationRequest presentation);

  Long deletePresentation(Long id);
}
