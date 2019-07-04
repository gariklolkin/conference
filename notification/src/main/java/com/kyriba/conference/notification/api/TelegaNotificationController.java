package com.kyriba.conference.notification.api;

import com.kyriba.conference.notification.domain.dto.NotificationStatus;
import com.kyriba.conference.notification.domain.dto.TelegaNotificationMessage;
import com.kyriba.conference.notification.services.TelegaNotificationRequestService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping(value = "/api/notification/telega", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE})
@Api(value = "Telegram messenger notification endpoint")
public class TelegaNotificationController
{
  private final TelegaNotificationRequestService telegaNotificationRequestService;

  @ApiOperation(value = "Sends Telegram messenger notification message", response = NotificationStatus.class)
  @ApiImplicitParams({ @ApiImplicitParam(name = "Authorization", required = true, dataType = "string",
      paramType = "header", defaultValue = "Bearer ") })
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK", response = NotificationStatus.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = String.class)
  })
  @PostMapping(value = "/notify")
  public ResponseEntity<NotificationStatus> sendNotification(@Validated @RequestBody @ApiParam(value = "Telegram notification message", required = true) TelegaNotificationMessage telegaNotificationMessage) {
    return ResponseEntity.ok(telegaNotificationRequestService.sendNotification(telegaNotificationMessage));
  }
}
