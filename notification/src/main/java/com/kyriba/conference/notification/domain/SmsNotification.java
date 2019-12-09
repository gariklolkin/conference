package com.kyriba.conference.notification.domain;

import com.kyriba.conference.notification.domain.dto.MessageType;
import com.kyriba.conference.notification.domain.dto.SmsNotificationMessage;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "sms_notification",
        indexes = @Index(name = "IX_SMSNOTIFICATION_INFO", columnList = "notification_info_id"),
        uniqueConstraints = @UniqueConstraint(name = "UK_SMSNOTIFICATION_INFO", columnNames = {"notification_info_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SmsNotification implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "notification_info_id", referencedColumnName = "id", nullable = false)
    private NotificationInfo notificationInfo;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumns({@JoinColumn(name = "notification_id", referencedColumnName = "id"),
            @JoinColumn(name = "notification_info_id", referencedColumnName = "notification_info_id")
    })
    private Set<RecipientInfo> recipients;

    private String body;

    public static SmsNotification getInstance(SmsNotificationMessage smsNotificationMessage) {
        return SmsNotification.builder()
                .recipients(RecipientInfo.getInstances(smsNotificationMessage.getRecipients()))
                .body(smsNotificationMessage.getBody())
                .notificationInfo(NotificationInfo.getAcceptedInfo(MessageType.SMS))
                .build();

    }
}
