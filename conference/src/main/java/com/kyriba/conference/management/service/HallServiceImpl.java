package com.kyriba.conference.management.service;

import com.kyriba.conference.management.dao.HallRepository;
import com.kyriba.conference.management.api.dto.HallRequest;
import com.kyriba.conference.management.api.dto.HallResponse;
import com.kyriba.conference.management.domain.Hall;
import com.kyriba.conference.management.domain.exception.EntityNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.StreamSupport.stream;


@Service
public class HallServiceImpl implements HallService
{
  private final HallRepository hallRepository;


  @Autowired
  public HallServiceImpl(HallRepository hallRepository)
  {
    this.hallRepository = hallRepository;
  }


  @Override
  public Optional<HallResponse> findHall(Long id)
  {
    return hallRepository.findById(id)
        .map(HallResponse::new);
  }

  @Override
  public List<HallResponse> findAllHalls()
  {
    return stream(hallRepository.findAll().spliterator(), false)
        .map(HallResponse::new)
        .collect(Collectors.toList());
  }


  @Override
  public Long createHall(HallRequest hallRequest)
  {
    Hall hall = new Hall()
        .withName(hallRequest.getName())
        .withPlaces(hallRequest.getPlaces());
    return hallRepository.save(hall).getId();
  }


  @Override
  public void updateHall(Long id, HallRequest hallRequest) throws EntityNotFound
  {
    Optional<Hall> hall = hallRepository.findById(id);
    if (hall.isPresent()) {
      Hall updated = hall.get();
      updated.setName(hallRequest.getName());
      updated.setPlaces(hallRequest.getPlaces());
      hallRepository.save(updated);
    }
    else {
      throw new EntityNotFound("Hall not found");
    }
  }


  @Override
  public void removeHall(Long id)
  {
    hallRepository.deleteById(id);
  }
}
