package com.kyriba.conference.notification.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.kyriba.conference.notification.configuration.RabbitMqReceiver.*;

@Configuration
public class RabbitMqConfiguration {


    private static final String MAIL_NOTIFICATION_QUEUE_NAME = "send-mail-notifications";
    private static final String TELEGA_NOTIFICATION_QUEUE_NAME = "send-telega-notifications";
    private static final String SMS_NOTIFICATION_QUEUE_NAME = "send-sms-notifications";

    public static final String SEND_NOTIFICATION_EXCHANGE_NAME = "send-notifications-exchange";


    @Bean
    public Queue sendMailQueue() {
        return new Queue(MAIL_NOTIFICATION_QUEUE_NAME);
    }

    @Bean
    public Queue sendTelegaQueue() {
        return new Queue(TELEGA_NOTIFICATION_QUEUE_NAME);
    }

    @Bean
    public Queue sendSmsQueue() {
        return new Queue(SMS_NOTIFICATION_QUEUE_NAME);
    }


    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(SEND_NOTIFICATION_EXCHANGE_NAME);
    }

    @Bean
    public AsyncRabbitTemplate asyncRabbitTemplate(RabbitTemplate rabbitTemplate) {
        return new AsyncRabbitTemplate(rabbitTemplate);
    }

    @Bean
    Binding mailBinding(@Qualifier("sendMailQueue") Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(EMAIL_ROUTING);
    }

    @Bean
    Binding telegaBinding(@Qualifier("sendTelegaQueue") Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(TELEGA_ROUTING);
    }

    @Bean
    Binding smsBinding(@Qualifier("sendSmsQueue") Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(SMS_ROUTING);
    }

    @Bean
    SimpleMessageListenerContainer emailContainer(ConnectionFactory connectionFactory,
                                                  @Qualifier("emailListenerAdapter") MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(MAIL_NOTIFICATION_QUEUE_NAME);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    SimpleMessageListenerContainer telegaContainer(ConnectionFactory connectionFactory,
                                                   @Qualifier("telegaListenerAdapter") MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(TELEGA_NOTIFICATION_QUEUE_NAME);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    SimpleMessageListenerContainer smsContainer(ConnectionFactory connectionFactory,
                                                @Qualifier("smsListenerAdapter") MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(SMS_NOTIFICATION_QUEUE_NAME);
        container.setMessageListener(listenerAdapter);
        return container;
    }


    @Bean
    MessageListenerAdapter emailListenerAdapter(RabbitMqReceiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveEmailDeliveryNotification");
    }

    @Bean
    MessageListenerAdapter telegaListenerAdapter(RabbitMqReceiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveTelegalDeliveryNotification");
    }

    @Bean
    MessageListenerAdapter smsListenerAdapter(RabbitMqReceiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveSmsDeliveryNotification");
    }


}
