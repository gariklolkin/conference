package kafka

import org.springframework.cloud.contract.spec.Contract


/**
 * @author Aliaksandr Samal
 */
Contract.make {
    label("trigger")
    input {
        triggeredBy("trigger()")
    }
    outputMessage {
        sentTo("NotificationTopic")
        body([
                "recipients"         : [
                        "name"   : "Mr. Smith",
                        "address": "xxx@yyy.com"
                ],
                "body"               : "Sponsor Mr. Smith has bean registered",
                "subject"            : "Sponsor registration notification",
                "emailMessageOptions": [
                        "HIGH_PRIORITY"
                ]
        ])
    }
}
