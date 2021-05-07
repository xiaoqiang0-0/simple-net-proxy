package org.example.simplenetproxy.server.management;


import io.netty.channel.Channel;

public interface TransformManagement {
    Channel getLocalChannel(String host);

    Channel getRemoteChannel(String remoteChannelId);

    void registerLocalChannel(String host, Channel channel);

    void registerRemoteChannel(Channel channel);

}
