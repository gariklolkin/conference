package com.kyriba.conference.notification.services;

import com.kyriba.conference.notification.dao.EmailNotificationRepository;
import com.kyriba.conference.notification.dao.NotificationInfoRepository;
import com.kyriba.conference.notification.dao.SmsNotificationRepository;
import com.kyriba.conference.notification.dao.TelegaNotificationRepository;
import com.kyriba.conference.notification.domain.EmailNotification;
import com.kyriba.conference.notification.domain.NotificationInfo;
import com.kyriba.conference.notification.domain.SmsNotification;
import com.kyriba.conference.notification.domain.TelegaNotification;
import com.kyriba.conference.notification.domain.dto.MessageStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DeliveryService {

    private final NotificationInfoRepository notificationInfoRepository;

    private final EmailNotificationRepository emailNotificationRepository;
    private final TelegaNotificationRepository telegaNotificationRepository;
    private final SmsNotificationRepository smsNotificationRepository;

    @Transactional
    public boolean deliveryEmailNotification(Long id) {
        return deliveryNotification(id, this::deliveryEmail);
    }

    @Transactional
    public boolean deliveryTelegaNotification(Long id) {
        return deliveryNotification(id, this::deliveryTelega);
    }

    @Transactional
    public boolean deliverySmsNotification(Long id) {
        return deliveryNotification(id, this::deliverySms);
    }

    private boolean deliveryEmail(NotificationInfo notificationInfo) {
        EmailNotification emailNotification = emailNotificationRepository.findByNotificationInfo(notificationInfo).orElse(null);

        if (emailNotification == null) {
            return true;
        }

        if (deliveredOrNotDeliverd()) {
            emailNotificationRepository.delete(emailNotification);
            return true;
        }

        return false;
    }

    private boolean deliveryTelega(NotificationInfo notificationInfo) {
        TelegaNotification telegaNotification = telegaNotificationRepository.findByNotificationInfo(notificationInfo).orElse(null);

        if (telegaNotification == null) {
            return true;
        }

        if (deliveredOrNotDeliverd()) {
            telegaNotificationRepository.delete(telegaNotification);
            return true;
        }

        return false;
    }

    private boolean deliverySms(NotificationInfo notificationInfo) {
        SmsNotification smsNotification = smsNotificationRepository.findByNotificationInfo(notificationInfo).orElse(null);

        if (smsNotification == null) {
            return true;
        }

        if (deliveredOrNotDeliverd()) {
            smsNotificationRepository.delete(smsNotification);
            return true;
        }

        return false;
    }


    private boolean deliveredOrNotDeliverd() {
        //fake method to emulate external services

        Random r = new Random();
        return r.nextBoolean();
    }

    private boolean deliveryNotification(Long id, Predicate<NotificationInfo> delivery) {
        NotificationInfo notificationInfo = notificationInfoRepository.findById(id).orElseThrow(DeliveryException::new);

        if (notificationInfo.isDelivered()) {
            return true;
        }

        if (delivery.test(notificationInfo)) {
            notificationInfo.setStatus(MessageStatus.DELIVERED);
            notificationInfoRepository.save(notificationInfo);
            return true;
        }

        return false;
    }

}
