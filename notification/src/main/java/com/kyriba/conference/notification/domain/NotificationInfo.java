package com.kyriba.conference.notification.domain;

import com.kyriba.conference.notification.domain.dto.MessageStatus;
import com.kyriba.conference.notification.domain.dto.MessageType;
import com.kyriba.conference.notification.domain.dto.NotificationStatus;
import com.kyriba.conference.notification.configuration.RabbitMqConfiguration;
import lombok.*;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import java.io.Serializable;

import static com.kyriba.conference.notification.configuration.RabbitMqReceiver.EMAIL_ROUTING;
import static com.kyriba.conference.notification.configuration.RabbitMqReceiver.SMS_ROUTING;
import static com.kyriba.conference.notification.configuration.RabbitMqReceiver.TELEGA_ROUTING;

@Entity
@Table(name = "notification_info",
        indexes = {@Index(name = "IX_NOTIFICATION_STATUS", columnList = "status"),
                @Index(name = "IX_NOTIFICATION_TYPE", columnList = "type")})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private MessageStatus status;

    @Enumerated(EnumType.STRING)
    private MessageType type;

    public static NotificationInfo getAcceptedInfo(MessageType type){
        return NotificationInfo.builder().type(type).status(MessageStatus.ACCEPTED).build();
    }

    public NotificationStatus toNotificationStatus() {
        return NotificationStatus.builder().messageId(id.toString()).messageType(type).messageStatus(status).build();
    }

    public boolean isDelivered() {
        return getStatus().equals(MessageStatus.DELIVERED);
    }

    public boolean sendToDeliveryQueue(AsyncRabbitTemplate rabbitTemplate) {

        if (getId() == null) {
            throw new IllegalStateException("Notification is not saved in the database");
        }

        if (isDelivered()) {
            return false;
        }

        String routingKey;

        switch (getType()) {
            case EMAIL:
                routingKey = EMAIL_ROUTING;
                break;
            case TELEGA:
                routingKey = TELEGA_ROUTING;
                break;
            case SMS:
                routingKey = SMS_ROUTING;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + getType());
        }

        try {
            rabbitTemplate.convertSendAndReceive(RabbitMqConfiguration.SEND_NOTIFICATION_EXCHANGE_NAME, routingKey, getId());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
