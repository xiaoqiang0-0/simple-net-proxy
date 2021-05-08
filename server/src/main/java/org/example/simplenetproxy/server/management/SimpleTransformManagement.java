package org.example.simplenetproxy.server.management;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleTransformManagement implements TransformManagement {

    private final Map<String, Channel> remoteChannelCacheMap;
    private final Map<String, Channel> localChannelCacheMap;

    public SimpleTransformManagement() {
        this.remoteChannelCacheMap = new ConcurrentHashMap<>();
        this.localChannelCacheMap = new ConcurrentHashMap<>();
    }

    @Override
    public Channel getLocalChannel(String host) {
        return localChannelCacheMap.get(host);
    }

    @Override
    public Channel getRemoteChannel(String remoteChannelId) {
        return remoteChannelCacheMap.get(remoteChannelId);
    }

    @Override
    public void registerLocalChannel(String host, Channel channel) {
        localChannelCacheMap.put(host, channel);
    }

    @Override
    public void registerRemoteChannel(Channel channel) {
        remoteChannelCacheMap.put(channel.id().asShortText(), channel);
    }


}
