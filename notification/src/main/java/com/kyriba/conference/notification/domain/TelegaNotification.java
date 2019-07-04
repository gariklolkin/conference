package com.kyriba.conference.notification.domain;

import com.kyriba.conference.notification.domain.dto.MessageType;
import com.kyriba.conference.notification.domain.dto.TelegaNotificationMessage;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "telega_notification",
        indexes = @Index(name = "IX_TELEGANOTIFICATION_INFO", columnList = "notification_info_id"),
        uniqueConstraints = @UniqueConstraint(name = "UK_TELEGANOTIFICATION_INFO", columnNames = {"notification_info_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TelegaNotification implements Serializable {
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

    public static TelegaNotification getInstance(TelegaNotificationMessage telegaNotificationMessage) {
        return TelegaNotification.builder()
                .recipients(RecipientInfo.getInstances(telegaNotificationMessage.getRecipients()))
                .body(telegaNotificationMessage.getBody())
                .notificationInfo(NotificationInfo.getAcceptedInfo(MessageType.TELEGA))
                .build();
    }
}
