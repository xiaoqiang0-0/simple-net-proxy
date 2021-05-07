package org.example.simplenetproxy.server.server;

import org.example.simplenetproxy.server.config.ConfigProperties;
import org.springframework.stereotype.Component;

@Component
public class LocalListenerServer implements Server {

    private final ConfigProperties properties;

    public LocalListenerServer(ConfigProperties properties) {
        this.properties = properties;
    }

    @Override
    public void start() {

    }
}
