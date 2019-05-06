package com.sharenotes.app.models.kafka;

import lombok.*;

/**
 * User details to pass through Kafka
 */
@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsDto {
    private String id;
    private String username;
    private String email;
    private String fullName;
}