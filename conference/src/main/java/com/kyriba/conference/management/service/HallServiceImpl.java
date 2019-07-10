package com.kyriba.conference.management.service;

import com.kyriba.conference.management.dao.HallRepository;
import com.kyriba.conference.management.domain.Hall;
import com.kyriba.conference.management.domain.dto.HallRequest;
import com.kyriba.conference.management.domain.dto.HallResponse;
import com.kyriba.conference.management.domain.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
@AllArgsConstructor
public class HallServiceImpl implements HallService
{
  private static final String HALL_NOT_FOUND = "Hall not found.";

  private final HallRepository hallRepository;


  @Override
  public HallResponse findHall(long id)
  {
    return hallRepository.findById(id)
        .map(HallResponse::new)
        .orElseThrow(() -> new EntityNotFoundException(HALL_NOT_FOUND));
  }


  @Override
  public List<HallResponse> findAllHalls()
  {
    return hallRepository.findAll().stream()
        .map(HallResponse::new)
        .collect(Collectors.toList());
  }


  @Override
  public long createHall(HallRequest hallRequest)
  {
    Hall hall = new Hall(hallRequest);
    return hallRepository.save(hall).getId();
  }


  @Override
  public void updateHall(long id, HallRequest hallRequest)
  {
    Hall hall = hallRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(HALL_NOT_FOUND));

    hall.update(hallRequest);
    hallRepository.save(hall);
  }


  @Override
  public void removeHall(long id)
  {
    hallRepository.deleteById(id);
  }
}
