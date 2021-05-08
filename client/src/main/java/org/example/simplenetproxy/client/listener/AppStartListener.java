package org.example.simplenetproxy.client.listener;

import org.example.simplenetproxy.client.server.LocalProxyService;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;

@Component
public class AppStartListener implements ApplicationListener<ApplicationStartedEvent> {
    private final Executor asyncServiceExecutor;
    private final LocalProxyService localProxyService;

    public AppStartListener(Executor asyncServiceExecutor, LocalProxyService localListenerServer) {
        this.asyncServiceExecutor = asyncServiceExecutor;
        this.localProxyService = localListenerServer;
    }

    @Override
    public void onApplicationEvent(ApplicationStartedEvent applicationStartedEvent) {
        asyncServiceExecutor.execute(localProxyService::start);
    }
}
