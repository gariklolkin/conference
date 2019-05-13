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
  public Long createHall(HallRequest hall)
  {
    return null;
  }


  @Override
  public Long updateHall(Long id, HallRequest hall)
  {
    return null;
  }


  @Override
  public Long deleteHall(Long id)
  {
    return null;
  }
}
