package com.kyriba.conference.management.service;

import com.kyriba.conference.management.domain.dto.PresentationRequest;
import com.kyriba.conference.management.domain.dto.PresentationResponse;

import java.util.List;
import java.util.Optional;


public interface ScheduleService
{
  List<PresentationResponse> getSchedule();

  long addPresentation(PresentationRequest presentation);

  Optional<PresentationResponse> getPresentation(long id);

  void updatePresentation(long id, PresentationRequest presentation);

  void deletePresentation(long id);
}
