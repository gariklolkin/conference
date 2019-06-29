package com.kyriba.conference.management.service;

import com.kyriba.conference.management.api.dto.PresentationRequest;
import com.kyriba.conference.management.api.dto.TopicDto;
import com.kyriba.conference.management.dao.HallRepository;
import com.kyriba.conference.management.dao.PresentationRepository;
import com.kyriba.conference.management.domain.Hall;
import com.kyriba.conference.management.domain.Presentation;
import com.kyriba.conference.management.domain.Topic;
import com.kyriba.conference.management.domain.exception.LinkedEntityNotFound;
import com.kyriba.conference.management.domain.exception.PresentationTimeIntersectionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.lang.String.format;


@Service
@Transactional
public class ScheduleServiceImpl implements ScheduleService
{
  private static final String HALL_NOT_FOUND = "Hall not found.";

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


  @Override
  public long addPresentation(PresentationRequest presentationRequest)
  {
    Hall hall = hallRepository.findById(presentationRequest.getHall())
        .orElseThrow(() -> new LinkedEntityNotFound(HALL_NOT_FOUND));

    TopicDto topic = presentationRequest.getTopic();
    Presentation presentation = Presentation.builder()
        .hall(hall)
        .topic(new Topic(topic.getTitle(), topic.getAuthor()))
        .startTime(presentationRequest.getStartTime())
        .endTime(presentationRequest.getEndTime())
        .build();
    presentation.validate();
    validateTimeIntersection(presentation);

    return presentationRepository.save(presentation).getId();
  }


  @Override
  public Optional<Presentation> getPresentation(long id)
  {
    return presentationRepository.findById(id);
  }


  @Transactional
  @Override
  public void updatePresentation(long id, PresentationRequest presentationRequest)
  {
    Presentation presentation = presentationRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Presentation not found."));

    Hall hall = hallRepository.findById(presentationRequest.getHall())
        .orElseThrow(() -> new LinkedEntityNotFound(HALL_NOT_FOUND));

    presentation.setHall(hall);
    presentation
        .setTopic(new Topic(presentationRequest.getTopic().getTitle(), presentationRequest.getTopic().getAuthor()));
    presentation.setStartTime(presentationRequest.getStartTime());
    presentation.setEndTime(presentationRequest.getEndTime());
    presentation.validate();
    validateTimeIntersection(presentation);

    presentationRepository.save(presentation);
  }


  @Override
  public void deletePresentation(long id)
  {
    presentationRepository.deleteById(id);
  }


  private void validateTimeIntersection(Presentation presentation)
  {
    final LocalTime startTime = presentation.getStartTime();
    final LocalTime endTime = presentation.getEndTime();

    try (Stream<Presentation> intersections = presentationRepository
        .findByStartTimeBetweenOrEndTimeBetween(startTime, endTime, startTime, endTime)) {

      Predicate<Presentation> anotherPresentation = pr -> !pr.getId().equals(presentation.getId());
      intersections
          .filter(anotherPresentation)
          .findAny()
          .ifPresent(pr -> {
                throw new PresentationTimeIntersectionException(
                    format("Presentation time intersection: wanted %s - %s, already reserved %s - %s.",
                        presentation.getStartTime(), presentation.getEndTime(), pr.getStartTime(), pr.getEndTime()));
              }
          );

    }
  }
}
