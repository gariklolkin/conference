package com.kyriba.conference.notification.api;

import com.kyriba.conference.notification.api.dto.MessageStatus;
import com.kyriba.conference.notification.api.dto.NotificationStatus;
import com.kyriba.conference.notification.services.NotificationStatusService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping(value = "/api/notification", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE})
@Validated
@Api(value = "Notification status endpoint")
public class NotificationStatusController
{
  private final NotificationStatusService notificationStatusService;

  @ApiOperation(value = "Retrieves status for a given message id", response = NotificationStatus.class)
  @ApiImplicitParams({ @ApiImplicitParam(name = "Authorization", required = true, dataType = "string",
      paramType = "header", defaultValue = "Bearer ") })
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK", response = NotificationStatus.class),
      @ApiResponse(code = 401, message = "Unauthorized"),
      @ApiResponse(code = 404, message = "Not Found")
  })
  @GetMapping(value = "/{id}/status")
  public ResponseEntity<NotificationStatus> getStatus(@ApiParam(value = "Notification message id to look a status for", required = true) @PathVariable(name = "id") String id) {
    return ResponseEntity.ok(notificationStatusService.getStatus(id));
  }

  @ApiOperation(value = "Retrieves all messages for a given status", response = NotificationStatus.class,
      responseContainer = "List")
  @ApiImplicitParams({ @ApiImplicitParam(name = "Authorization", required = true, dataType = "string",
      paramType = "header", defaultValue = "Bearer ") })
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK", response = NotificationStatus.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = String.class)
  })
  @GetMapping(value = "/status/{status}")
  public ResponseEntity<List<NotificationStatus>> getMessagesByStatus(@ApiParam(value = "Given notification message status", required = true) @PathVariable(name = "status") MessageStatus messageStatus) {
    return ResponseEntity.ok(notificationStatusService.getMessagesByStatus(messageStatus));
  }
}
