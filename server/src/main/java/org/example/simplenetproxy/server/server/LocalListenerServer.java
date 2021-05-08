package org.example.simplenetproxy.server.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import org.example.simplenetproxy.server.config.ConfigProperties;
import org.example.simplenetproxy.server.config.LocalListenerServerChannelInitializer;
import org.springframework.stereotype.Component;


@Component
public class LocalListenerServer extends AbstractListenerServer {
    private final ConfigProperties properties;


    private final LocalListenerServerChannelInitializer initializer;


    public LocalListenerServer(ConfigProperties properties, EventLoopGroup parentGroup, EventLoopGroup childGroup, LocalListenerServerChannelInitializer initializer) {
        super(new ServerBootstrap(), parentGroup, childGroup);
        this.properties = properties;
        this.initializer = initializer;
        this.port = properties.getLocalPort();
    }

    @Override
    public LocalListenerServerChannelInitializer getInitializer() {
        return initializer;
    }
}
