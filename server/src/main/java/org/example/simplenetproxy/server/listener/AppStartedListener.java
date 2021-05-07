package org.example.simplenetproxy.server.listener;

import org.example.simplenetproxy.server.server.LocalListenerServer;
import org.example.simplenetproxy.server.server.OuterListenerServer;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class AppStartedListener implements ApplicationListener<ApplicationStartedEvent> {
    private final LocalListenerServer localListenerServer;
    private final OuterListenerServer outerListenerServer;

    public AppStartedListener(LocalListenerServer localListenerServer, OuterListenerServer outerListenerServer) {
        this.localListenerServer = localListenerServer;
        this.outerListenerServer = outerListenerServer;
    }

    @Override
    public void onApplicationEvent(ApplicationStartedEvent applicationStartedEvent) {
        localListenerServer.start();
        outerListenerServer.start();
    }
}
