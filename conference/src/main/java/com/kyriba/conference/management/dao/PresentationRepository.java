package com.kyriba.conference.management.dao;

import com.kyriba.conference.management.domain.Hall;
import com.kyriba.conference.management.domain.Presentation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalTime;
import java.util.stream.Stream;


public interface PresentationRepository extends JpaRepository<Presentation, Long>
{
  Stream<Presentation> findByHallAndStartTimeBetweenOrEndTimeBetween(Hall hall,
                                                                     LocalTime startTimeFrom, LocalTime startTimeTo,
                                                                     LocalTime endTimeFrom, LocalTime endTimeTo);
}
