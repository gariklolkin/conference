package com.kyriba.conference.management.service;

import com.kyriba.conference.management.api.dto.PresentationRequest;
import com.kyriba.conference.management.domain.Presentation;

import java.util.Optional;


public interface ScheduleService
{
  Iterable<Presentation> getSchedule();

  long addPresentation(PresentationRequest presentation);

  Optional<Presentation> getPresentation(long id);

  void updatePresentation(long id, PresentationRequest presentation);

  void deletePresentation(long id);
}
