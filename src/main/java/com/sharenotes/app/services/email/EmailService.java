package com.sharenotes.app.services.email;

import com.sharenotes.app.models.kafka.UserDetailsDto;
import com.sharenotes.app.models.user.User;
import org.apache.kafka.clients.consumer.ConsumerRecord;

/**
 * Email service to send email messages to users
 */
public interface EmailService {

    /**
     * listen to a kafka topic for sending email to new users
     * @param consumerRecord - for kafka, contains key - userName, value - UserDetailsDto
     * @see UserDetailsDto
     */
    void onUserCreated(ConsumerRecord<String, String> consumerRecord);

    /**
     * send an email to validate user by email when password has changed
     * @param user
     */
    void onPasswordChanged(User user);

    /**
     * General email sender method
     * @param to - the email address of the recipient
     * @param subject - email subject
     * @param body - email content
     */
    void sendEmail(String to, String subject, String body);
}
