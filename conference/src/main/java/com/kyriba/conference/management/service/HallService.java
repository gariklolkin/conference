package com.kyriba.conference.management.service;

import com.kyriba.conference.management.domain.dto.HallRequest;
import com.kyriba.conference.management.domain.dto.HallResponse;

import java.util.List;


public interface HallService
{
  HallResponse findHall(long id);

  List<HallResponse> findAllHalls();

  long createHall(HallRequest hallRequest);

  void updateHall(long id, HallRequest hallRequest);

  void removeHall(long id);
}
