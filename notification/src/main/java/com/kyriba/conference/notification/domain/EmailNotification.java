package com.kyriba.conference.notification.domain;

import com.kyriba.conference.notification.domain.dto.EmailMessageOptions;
import com.kyriba.conference.notification.domain.dto.EmailNotificationMessage;
import com.kyriba.conference.notification.domain.dto.MessageType;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "email_notification",
        indexes = @Index(name = "IX_EMAILNOTIFICATION_INFO", columnList = "notification_info_id"),
        uniqueConstraints = @UniqueConstraint(name = "UK_EMAILNOTIFICATION_INFO", columnNames = {"notification_info_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailNotification implements Serializable {

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

    private String subject;

    private String body;

    @ElementCollection(targetClass = EmailMessageOptions.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "email_option", joinColumns = {@JoinColumn(name = "email_id")})
    @Column(name = "option")
    private Set<EmailMessageOptions> emailMessageOptions;


    public static EmailNotification getInstance(EmailNotificationMessage emailNotificationMessage) {
        return EmailNotification.builder()
                .recipients(RecipientInfo.getInstances(emailNotificationMessage.getRecipients()))
                .body(emailNotificationMessage.getBody())
                .subject(emailNotificationMessage.getSubject())
                .emailMessageOptions(emailNotificationMessage.getEmailMessageOptions())
                .notificationInfo(NotificationInfo.getAcceptedInfo(MessageType.EMAIL))
                .build();
    }

}
