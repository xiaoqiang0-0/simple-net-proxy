package org.example.simplenetproxy.server.config;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import org.example.simplenetproxy.server.handler.ServerOuterHandler;
import org.springframework.stereotype.Component;

@Component
public class OuterListenerServerChannelInitializer extends ChannelInitializer {
    private final ServerOuterHandler serverOuterHandler;

    public OuterListenerServerChannelInitializer(ServerOuterHandler serverOuterHandler) {
        this.serverOuterHandler = serverOuterHandler;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ch.pipeline().addLast(serverOuterHandler);
    }
}
