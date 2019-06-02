package com.kyriba.conference.management.service;

import com.kyriba.conference.management.api.dto.PresentationRequest;
import com.kyriba.conference.management.domain.Presentation;

import java.util.List;


public interface ScheduleService
{
  List<Presentation> getSchedule();

  Long addPresentation(PresentationRequest presentation);

  Presentation getPresentation();

  Long updatePresentation(Long id, PresentationRequest presentation);

  Long deletePresentation(Long id);
}
