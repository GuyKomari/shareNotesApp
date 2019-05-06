package com.sharenotes.app.configurations;

import com.sharenotes.app.utils.note.NoteDateComparator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Comparator;

/**
 * General application configuration
 * @author Guy Komari 30/03/2019.
 */
@EnableAsync
@Configuration
@EnableMongoAuditing
@EnableScheduling
public class AppConfig {

    /**
     * create a bean of type - passwordEncoder
     * @see BCryptPasswordEncoder
     * @return user password encoder instance
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    /**
     * note comparator by note timeUpdated
     * @see com.sharenotes.app.models.note.Note
     * @see NoteDateComparator
     * @return NoteDateComparator instance
     */
    @Bean
    @Qualifier("noteDateComparator")
    public Comparator noteDateComparator(){
        return new NoteDateComparator();
    }
}
