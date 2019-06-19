package com.kyriba.conference.management.service;

import com.kyriba.conference.management.api.dto.PresentationRequest;
import com.kyriba.conference.management.api.dto.TopicDto;
import com.kyriba.conference.management.dao.HallRepository;
import com.kyriba.conference.management.dao.PresentationRepository;
import com.kyriba.conference.management.domain.Hall;
import com.kyriba.conference.management.domain.Presentation;
import com.kyriba.conference.management.domain.Topic;
import com.kyriba.conference.management.domain.exception.EntityNotFound;
import com.kyriba.conference.management.domain.exception.LinkedEntityNotFound;
import com.kyriba.conference.management.domain.exception.TimeslotIsBooked;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Stream;


@Service
public class ScheduleServiceImpl implements ScheduleService
{
  private final PresentationRepository presentationRepository;

  private final HallRepository hallRepository;


  @Autowired
  public ScheduleServiceImpl(PresentationRepository presentationRepository,
                             HallRepository hallRepository)
  {
    this.presentationRepository = presentationRepository;
    this.hallRepository = hallRepository;
  }


  @Override
  public Iterable<Presentation> getSchedule()
  {
    return presentationRepository.findAll();
  }


  @Transactional
  @Override
  public Long addPresentation(PresentationRequest presentationRequest) throws LinkedEntityNotFound
  {
    Optional<Hall> hall = hallRepository.findById(presentationRequest.getHall());
    if (hall.isPresent()) {
      TopicDto topic = presentationRequest.getTopic();
      Presentation presentation = Presentation.builder()
          .hall(hall.get())
          .topic(new Topic(topic.getTitle(), topic.getAuthor()))
          .startTime(presentationRequest.getStartTime())
          .endTime(presentationRequest.getEndTime())
          .build();
      presentation.validate();
      validateTimeIntersection(presentation);

      return presentationRepository.save(presentation).getId();
    }
    else {
      throw new LinkedEntityNotFound("Hall not found");
    }
  }


  private void validateTimeIntersection(Presentation presentation)
  {
    try (Stream<Presentation> intersections = presentationRepository
        .findByStartTimeBetweenOrEndTimeBetween(presentation.getStartTime(), presentation.getEndTime(),
            presentation.getStartTime(), presentation.getEndTime())) {
      if (intersections.anyMatch(pr -> !pr.getId().equals(presentation.getId()))) {
        throw new TimeslotIsBooked("Timeslot is booked");
      }
    }
  }


  @Override
  public Optional<Presentation> getPresentation(Long id)
  {
    return presentationRepository.findById(id);
  }


  @Transactional
  @Override
  public void updatePresentation(Long id, PresentationRequest presentationRequest) throws EntityNotFound,
      LinkedEntityNotFound
  {
    Optional<Presentation> presentation = presentationRepository.findById(id);
    if (presentation.isPresent()) {
      Presentation updated = presentation.get();

      Hall hall = hallRepository.findById(presentationRequest.getHall())
          .orElseThrow(() -> new LinkedEntityNotFound("Hall not found"));
      updated.setHall(hall);
      updated
          .setTopic(new Topic(presentationRequest.getTopic().getTitle(), presentationRequest.getTopic().getAuthor()));
      updated.setStartTime(presentationRequest.getStartTime());
      updated.setEndTime(presentationRequest.getEndTime());
      updated.validate();
      validateTimeIntersection(updated);

      presentationRepository.save(updated);
    }
    else {
      throw new EntityNotFound("Presentation not found");
    }
  }


  @Override
  public void deletePresentation(Long id)
  {
    presentationRepository.deleteById(id);
  }
}
