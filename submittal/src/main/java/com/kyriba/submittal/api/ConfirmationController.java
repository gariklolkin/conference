package com.kyriba.submittal.api;

import com.kyriba.submittal.service.SpeakerService;
import com.kyriba.submittal.service.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;


/**
 * @author M-ABL
 */
@RestController
@RequestMapping(value = "${api.version.path}/confirm",
    consumes = APPLICATION_JSON_UTF8_VALUE,
    produces = APPLICATION_JSON_UTF8_VALUE)
@RequiredArgsConstructor
public class ConfirmationController
{
  private final SpeakerService speakerService;


  @ExceptionHandler(ObjectNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  void handleObjectNotFound()
  {
  }


  @PostMapping(path = "/registration")
  void confirmRegistration(@Valid @RequestBody RegistrationConfirmRequest confirm)
  {
    speakerService.finishRegistration(confirm.key);
  }


  @Setter
  private static class RegistrationConfirmRequest
  {
    @NotBlank
    private String key;
  }

}
