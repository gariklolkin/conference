package com.kyriba.conference.management.service;

import com.kyriba.conference.management.api.dto.HallRequest;
import com.kyriba.conference.management.api.dto.HallResponse;
import com.kyriba.conference.management.domain.exception.EntityNotFound;

import java.util.List;
import java.util.Optional;


public interface HallService
{
  Optional<HallResponse> findHall(Long id);

  List<HallResponse> findAllHalls();

  Long createHall(HallRequest hallRequest);

  void updateHall(Long id, HallRequest hallRequest) throws EntityNotFound;

  void removeHall(Long id);
}
