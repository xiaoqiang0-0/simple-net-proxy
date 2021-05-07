package org.example.simplenetproxy.core.proxy;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

public class ProxyPacketDecoder extends MessageToMessageDecoder<Package> {
    @Override
    protected void decode(ChannelHandlerContext ctx, Package msg, List<Object> out) throws Exception {

    }
}
