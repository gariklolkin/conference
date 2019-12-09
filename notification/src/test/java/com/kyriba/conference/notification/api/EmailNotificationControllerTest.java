package com.kyriba.conference.notification.api;

import com.kyriba.conference.notification.domain.dto.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.apache.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static junit.framework.TestCase.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static io.restassured.RestAssured.given;
import static junit.framework.TestCase.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class EmailNotificationControllerTest
{
  @Test
  public void emailNotificationShouldBeSent()
  {
    Set<Recipient> recipients = Stream.of(Recipient.builder().name("Name1").address("Address1").build(),
        Recipient.builder().name("Name2").address("Address2").build()).collect(Collectors.toSet());
    Set<EmailMessageOptions> emailMessageOptions = Stream.of(EmailMessageOptions.HIDE_ADDRESS, EmailMessageOptions.HIGH_PRIORITY).collect(Collectors.toSet());

    EmailNotificationMessage emailNotificationMessage = EmailNotificationMessage.builder().recipients(recipients).body("Email Body").subject("Subject").emailMessageOptions(emailMessageOptions).build();


    NotificationStatus status = given()
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .body(emailNotificationMessage)
        .when().post("/api/notification/email/notify")

        .then()
        .statusCode(HttpStatus.SC_OK)
        .contentType(APPLICATION_JSON_UTF8_VALUE)

        .extract().body().as(NotificationStatus.class);

    assertNotNull(status);
    assertEquals(status.getMessageStatus(), MessageStatus.ACCEPTED);
    assertNotNull(status.getMessageId());
    assertEquals(status.getMessageType(), MessageType.EMAIL);
  }
}
