package com.kyriba.conference.management.service;

import com.kyriba.conference.management.api.dto.HallRequest;
import com.kyriba.conference.management.domain.Hall;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class HallServiceImpl implements HallService
{
  @Override
  public Optional<Hall> find(Long id)
  {
    return Optional.empty();
  }


  @Override
  public List<Hall> findAll()
  {
    return null;
  }


  @Override
  public Hall createHall(HallRequest hall)
  {
    return null;
  }


  @Override
  public void updateHall(Long id, HallRequest hall)
  {
  }


  @Override
  public void deleteHall(Long id)
  {
  }
}
