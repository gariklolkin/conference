package com.kyriba.conference.service;


import com.kyriba.conference.api.dto.HallRequest;
import com.kyriba.conference.domain.Hall;

import java.util.List;


public interface HallService
{
  List<Hall> findAll();

  Long createHall(HallRequest hall);

  Long updateHall(Long id, HallRequest hall);

  Long deleteHall(Long id);
}
