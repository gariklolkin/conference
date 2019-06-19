package com.kyriba.conference.management.service;

import com.kyriba.conference.management.api.dto.PresentationRequest;
import com.kyriba.conference.management.domain.Presentation;
import com.kyriba.conference.management.domain.exception.EntityNotFound;
import com.kyriba.conference.management.domain.exception.LinkedEntityNotFound;

import java.util.Optional;


public interface ScheduleService
{
  Iterable<Presentation> getSchedule();

  Long addPresentation(PresentationRequest presentation) throws LinkedEntityNotFound;

  Optional<Presentation> getPresentation(Long id);

  void updatePresentation(Long id, PresentationRequest presentation) throws EntityNotFound, LinkedEntityNotFound;

  void deletePresentation(Long id);
}
