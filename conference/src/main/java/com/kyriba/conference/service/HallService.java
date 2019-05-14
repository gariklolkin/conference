package com.kyriba.conference.service;


import com.kyriba.conference.api.dto.HallRequest;
import com.kyriba.conference.domain.Hall;

import java.util.List;


public interface HallService
{
  List<Hall> findAll();

  Hall createHall(HallRequest hall);

  Hall updateHall(Long id, HallRequest hall);

  void deleteHall(Long id);
}
