package com.sharenotes.app.services.kafka;

import com.sharenotes.app.consts.KafkaTopics;
import com.sharenotes.app.models.kafka.UserDetailsDto;
import com.sharenotes.app.models.user.User;
import io.vertx.core.json.Json;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerServiceImpl implements KafkaProducerService {
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Async
    @Override
    public void onNewUser(User user) {
        userEvent(user, KafkaTopics.newUserTopic);
    }

    private void userEvent(User user, String topic) {
        UserDetailsDto userDetails = UserDetailsDto.builder().username(user.getUsername()).email(user.getEmail())
                .fullName(user.getFullName()).id(user.getId()).build();
        ProducerRecord<String, String> producerRecord =
                new ProducerRecord<>(topic, userDetails.getId(), Json.encode(userDetails));
        kafkaTemplate.send(producerRecord);
    }
}
