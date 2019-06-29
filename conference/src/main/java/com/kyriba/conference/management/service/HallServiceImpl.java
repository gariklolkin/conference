package com.kyriba.conference.management.service;

import com.kyriba.conference.management.api.dto.HallRequest;
import com.kyriba.conference.management.api.dto.HallResponse;
import com.kyriba.conference.management.dao.HallRepository;
import com.kyriba.conference.management.domain.Hall;
import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.StreamSupport.stream;


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
        .orElseThrow(() -> new ResourceNotFoundException(HALL_NOT_FOUND));
  }


  @Override
  public List<HallResponse> findAllHalls()
  {
    return stream(hallRepository.findAll().spliterator(), false)
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
        .map(h -> h.update(hallRequest))
        .orElseThrow(() -> new ResourceNotFoundException(HALL_NOT_FOUND));

    hallRepository.save(hall);
  }


  @Override
  public void removeHall(long id)
  {
    hallRepository.deleteById(id);
  }
}
