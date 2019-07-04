package com.kyriba.conference.notification.services;

import com.kyriba.conference.notification.domain.NotificationInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DeliverySchedulerService {
    private final NotificationStatusService notificationStatusService;
    private final AsyncRabbitTemplate asyncRabbitTemplate;

    @Scheduled(fixedDelay = (15 * 60 * 1000))
    public void sendUnsentNotifications() {
        Set<NotificationInfo> unsentNotifications = notificationStatusService.findUnsentNotifications();

        unsentNotifications.forEach(notificationInfo -> notificationInfo.sendToDeliveryQueue(asyncRabbitTemplate));
    }
}
