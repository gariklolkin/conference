package com.kyriba.conference.management.service;


import com.kyriba.conference.management.api.dto.HallRequest;
import com.kyriba.conference.management.domain.Hall;

import java.util.List;
import java.util.Optional;


public interface HallService
{
  List<Hall> findAll();

  Hall createHall(HallRequest hall);

  void updateHall(Long id, HallRequest hall);

  void deleteHall(Long id);

  Optional<Hall> find(Long id);
}
