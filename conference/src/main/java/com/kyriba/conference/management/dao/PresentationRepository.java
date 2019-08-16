package com.kyriba.conference.management.dao;

import com.kyriba.conference.management.domain.Hall;
import com.kyriba.conference.management.domain.Presentation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalTime;
import java.util.stream.Stream;


public interface PresentationRepository extends JpaRepository<Presentation, Long>
{
  @Query("SELECT p FROM Presentation p WHERE p.hall = :hall AND (p.startTime BETWEEN :startTime AND :endTime OR p.endTime BETWEEN :startTime AND :endTime)")
  Stream<Presentation> findByHallAndStartTimeOrEndTimeBetween(@Param("hall") Hall hall,
                                                              @Param("startTime") LocalTime startTime,
                                                              @Param("endTime") LocalTime endTime);
}
