package org.example.simplenetproxy.server.listener;

import org.example.simplenetproxy.server.server.LocalListenerServer;
import org.example.simplenetproxy.server.server.OuterListenerServer;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;

@Component
public class AppStartListener implements ApplicationListener<ApplicationStartedEvent> {
    private final LocalListenerServer localListenerServer;
    private final OuterListenerServer outerListenerServer;
    private final Executor asyncServiceExecutor;

    public AppStartListener(LocalListenerServer localListenerServer, OuterListenerServer outerListenerServer, Executor asyncServiceExecutor) {
        this.localListenerServer = localListenerServer;
        this.outerListenerServer = outerListenerServer;
        this.asyncServiceExecutor = asyncServiceExecutor;
    }

    @Override
    public void onApplicationEvent(ApplicationStartedEvent applicationStartedEvent) {
        asyncServiceExecutor.execute(localListenerServer::start);
        asyncServiceExecutor.execute(outerListenerServer::start);
    }
}
