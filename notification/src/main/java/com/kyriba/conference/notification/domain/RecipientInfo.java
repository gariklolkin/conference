package com.kyriba.conference.notification.domain;

import com.kyriba.conference.notification.domain.dto.Recipient;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "recipient_info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipientInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String address;

    public static RecipientInfo getInstance(Recipient recipient) {
        return RecipientInfo.builder().name(recipient.getName()).address(recipient.getAddress()).build();
    }

    public static Set<RecipientInfo> getInstances(Set<Recipient> recipients) {
        return recipients.stream().map(RecipientInfo::getInstance).collect(Collectors.toSet());
    }
}
