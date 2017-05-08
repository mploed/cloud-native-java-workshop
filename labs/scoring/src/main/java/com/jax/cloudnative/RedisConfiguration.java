package com.jax.cloudnative;

import com.jax.cloudnative.event.CreditApplicationEventReceiver;
import com.jax.cloudnative.event.payload.CreditApplicationHandedInEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

@Configuration
public class RedisConfiguration {

    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                            CreditApplicationEventReceiver creditApplicationEventReceiver

    ) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);

        container.addMessageListener(creditApplicationApprovedMessageListener(creditApplicationEventReceiver),
                new PatternTopic("applied-applications"));


        return container;
    }

    @Bean
    MessageListenerAdapter creditApplicationApprovedMessageListener(CreditApplicationEventReceiver receiver) {
        MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(receiver, "receiveMessage");
        messageListenerAdapter.setSerializer(new Jackson2JsonRedisSerializer<CreditApplicationHandedInEvent>(CreditApplicationHandedInEvent.class));
        return messageListenerAdapter;
    }
}