package com.kyriba.conference.management.service;


import com.kyriba.conference.management.api.dto.HallRequest;
import com.kyriba.conference.management.domain.Hall;

import java.util.List;


public interface HallService
{
  List<Hall> findAll();

  Hall createHall(HallRequest hall);

  Hall updateHall(Long id, HallRequest hall);

  void deleteHall(Long id);
}
