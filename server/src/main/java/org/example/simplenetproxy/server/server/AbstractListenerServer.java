package org.example.simplenetproxy.server.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.example.simplenetproxy.core.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

public abstract class AbstractListenerServer implements Server {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    protected volatile int state;
    protected final ServerBootstrap serverBootstrap;
    protected final EventLoopGroup parentGroup;
    protected final EventLoopGroup childGroup;
    protected int port;

    public AbstractListenerServer(ServerBootstrap serverBootstrap, EventLoopGroup parentGroup, EventLoopGroup childGroup) {
        this.serverBootstrap = serverBootstrap;
        this.parentGroup = parentGroup;
        this.childGroup = childGroup;
    }

    @Override
    public void start() {
        serverBootstrap.group(parentGroup, childGroup);
        serverBootstrap.channel(NioServerSocketChannel.class)
                .localAddress(new InetSocketAddress(port))
                .childHandler(getInitializer());
        try {
            ChannelFuture future = serverBootstrap.bind().sync();
            future.addListener(f -> {
                if (f.isSuccess()) {
                    state = STATE_ACTIVE;
                    logger.info("监听服务启动成功！ Port:[{}]", port);
                } else {
                    state = STATE_ERROR;
                    logger.error("监听服务启动失败！请检查配置信息");
                }
            });
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            logger.error("监听服务启动失败！msg: {}", e.getMessage());
        }
    }

    protected abstract ChannelInitializer getInitializer();

    @Override
    public void stop() {
        try {
            childGroup.shutdownGracefully().sync();
            parentGroup.shutdownGracefully().sync();
        } catch (InterruptedException e) {
            logger.error("监听服务关闭失败！msg: {}", e.getMessage());
        }
    }

    @Override
    public int state() {
        return state;
    }
}
