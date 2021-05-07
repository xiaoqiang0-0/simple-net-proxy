package org.example.simplenetproxy.server;

import org.example.simplenetproxy.core.auth.UserRepository;
import org.example.simplenetproxy.server.auth.SimpleUserRepository;
import org.example.simplenetproxy.server.management.SimpleTransformManagement;
import org.example.simplenetproxy.server.management.TransformManagement;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AppConfig {
    @Bean
    @ConditionalOnMissingBean(UserRepository.class)
    public UserRepository userRepository() {
        return new SimpleUserRepository();
    }

    @Bean
    @ConditionalOnMissingBean(TransformManagement.class)
    public TransformManagement transformManagement() {
        return new SimpleTransformManagement();
    }
}
