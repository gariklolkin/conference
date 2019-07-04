package com.kyriba.conference.notification.configuration;

import com.kyriba.conference.notification.domain.dto.MessageType;
import com.kyriba.conference.notification.services.DeliveryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RabbitMqReceiver {

    public static final String EMAIL_ROUTING = "conference.notification." + MessageType.EMAIL;
    public static final String SMS_ROUTING = "conference.notification." + MessageType.SMS;
    public static final String TELEGA_ROUTING = "conference.notification." + MessageType.TELEGA;

    private final DeliveryService deliveryService;

    public void receiveEmailDeliveryNotification(Long id) {

        log.info("Received Email Delivery Notification: " + id);
        deliveryService.deliveryEmailNotification(id);
    }

    public void receiveTelegalDeliveryNotification(Long id) {

        log.info("Received Telega Delivery Notification: " + id);
        deliveryService.deliveryTelegaNotification(id);
    }

    public void receiveSmsDeliveryNotification(Long id) {

        log.info("Received Sms Delivery Notification: " + id);
        deliveryService.deliverySmsNotification(id);
    }

}
