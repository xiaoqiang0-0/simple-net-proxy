package org.example.simplenetproxy.server.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import org.example.simplenetproxy.server.config.ConfigProperties;
import org.example.simplenetproxy.server.config.OuterListenerServerChannelInitializer;
import org.springframework.stereotype.Component;


@Component
public class OuterListenerServer extends AbstractListenerServer {

    private final ConfigProperties properties;

    private final OuterListenerServerChannelInitializer initializer;


    public OuterListenerServer(ConfigProperties properties, EventLoopGroup parentGroup, EventLoopGroup childGroup, OuterListenerServerChannelInitializer initializer) {
        super(new ServerBootstrap(), parentGroup, childGroup);
        this.properties = properties;
        this.initializer = initializer;
        this.port = properties.getOuterPort();
    }

    @Override
    public OuterListenerServerChannelInitializer getInitializer() {
        return initializer;
    }
}
