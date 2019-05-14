package com.kyriba.conference.service;

import com.kyriba.conference.api.dto.HallRequest;
import com.kyriba.conference.domain.Hall;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class HallServiceImpl implements HallService
{
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
  public Hall updateHall(Long id, HallRequest hall)
  {
    return null;
  }


  @Override
  public void deleteHall(Long id)
  {
  }
}
