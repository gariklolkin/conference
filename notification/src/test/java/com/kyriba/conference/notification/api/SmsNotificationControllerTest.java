package com.kyriba.conference.notification.api;

import com.kyriba.conference.notification.api.dto.MessageStatus;
import com.kyriba.conference.notification.api.dto.MessageType;
import com.kyriba.conference.notification.api.dto.NotificationStatus;
import com.kyriba.conference.notification.api.dto.Recipient;
import com.kyriba.conference.notification.api.dto.SmsNotificationMessage;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SmsNotificationControllerTest
{
  @Rule
  public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

  private RequestSpecification documentationSpec;


  @Before
  public void setUp() {
    this.documentationSpec = new RequestSpecBuilder()
        .addFilter(documentationConfiguration(restDocumentation))
        .build();
  }

  @Test
  public void smsNotificationShouldBeSent()
  {
    List<Recipient> recipients = Arrays.asList(Recipient.builder().name("Name1").address("Address1").build(),
        Recipient.builder().name("Name2").address("Address2").build());

    SmsNotificationMessage emailNotificationMessage = SmsNotificationMessage.builder().recipients(recipients).body("Email Body").build();


    NotificationStatus status = given(this.documentationSpec)
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .body(emailNotificationMessage)
        .filter(document("notification-sms-notify"))
        .when().post("/api/notification/sms/notify")

        .then()
        .statusCode(HttpStatus.SC_OK)
        .contentType(APPLICATION_JSON_UTF8_VALUE)

        .extract().body().as(NotificationStatus.class);

    assertNotNull(status);
    assertEquals(status.getMessageStatus(), MessageStatus.ACCEPTED);
    assertNotNull(status.getMessageId());
    assertEquals(status.getMessageType(), MessageType.SMS);
  }
}
