package com.kyriba.conference.sponsorship.service;

import com.kyriba.conference.sponsorship.domain.EmailMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * @author Aliaksandr Samal
 */
@Component
@RequiredArgsConstructor
public class EmailClientImpl implements EmailClient
{
  @Value("${notification.sync}")
  private boolean isSyncNotification;
  private final EmailClientSync emailClientSync;
  private final EmailClientAsync emailClientAsync;

  @Override
  public void sendNotification(EmailMessage emailMessage)
  {
    if (isSyncNotification) {
      try {
        emailClientSync.sendNotification(emailMessage);
      }
      catch (Exception e) {
        //todo it is workaround, what behavior is expected when the other service is not available?
        e.printStackTrace();
      }
    }
    else {
      emailClientAsync.sendNotification(emailMessage);
    }
  }
}