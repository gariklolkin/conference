package com.kyriba.conference.management.service;

import com.kyriba.conference.management.domain.dto.HallRequest;
import com.kyriba.conference.management.domain.dto.HallResponse;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;


public interface HallService
{
  HallResponse findHall(@Positive long id);

  List<HallResponse> findAllHalls();

  long createHall(@Valid HallRequest hallRequest);

  void updateHall(@Positive long id, @Valid HallRequest hallRequest);

  void removeHall(@Positive long id);
}
