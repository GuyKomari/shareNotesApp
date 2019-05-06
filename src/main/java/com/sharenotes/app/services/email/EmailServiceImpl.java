package com.sharenotes.app.services.email;

import com.sharenotes.app.consts.KafkaTopics;
import com.sharenotes.app.models.kafka.UserDetailsDto;
import com.sharenotes.app.models.user.User;
import io.vertx.core.json.Json;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender emailSender;
    private String address = "https://localhost";
    @Value("${server.port}") private Integer port;

    @Override
    @KafkaListener(topics = KafkaTopics.newUserTopic)
    public void onUserCreated(ConsumerRecord<String, String> consumerRecord) {
        UserDetailsDto userDetails = Json.decodeValue(consumerRecord.value(), UserDetailsDto.class);
        sendEmail(userDetails.getEmail(),
                String.format("A warm welcome %s for joining the ShareNotes App !", userDetails.getFullName()),
                String.format("Thank you for joining us!\n" +
                        "Please register your account -\n %s:%d/register/%s \n" +
                        "Your username is - %s" +
                        "\nEnjoy :) \n", address, port, Base64.getEncoder().encodeToString(userDetails.getId().getBytes()), userDetails.getUsername()));
    }

    @Async
    @Override
    public void onPasswordChanged(User user) {
        sendEmail(user.getEmail(),
                String.format("Hey %s your password has been updated successfully - please verify the changed", user.getFullName()),
                String.format("\nPlease verify your password -\n %s:%d/register/%s \n" +
                        "\n and continue to inUse the shareNotes app :) \n", address, port, Base64.getEncoder().encodeToString(user.getId().getBytes())));
    }

    public void sendEmail(String to, String subject, String body){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        emailSender.send(message);
    }
}

