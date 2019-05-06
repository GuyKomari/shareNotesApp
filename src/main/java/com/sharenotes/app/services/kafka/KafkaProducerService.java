package com.sharenotes.app.services.kafka;

import com.sharenotes.app.models.user.User;
import org.springframework.stereotype.Service;

/**
 * Apache Kafka service
 * @see com.sharenotes.app.consts.KafkaTopics
 */
@Service
public interface KafkaProducerService {

    /**
     * publish "new user" event to Kafka
     * @param user to build the userDetails body
     */
    void onNewUser(User user);
}
