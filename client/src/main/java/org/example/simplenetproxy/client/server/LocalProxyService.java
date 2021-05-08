package org.example.simplenetproxy.client.server;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.example.simplenetproxy.client.config.AppConfigProperties;
import org.example.simplenetproxy.client.config.LocalProxyServerChannelInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.net.InetSocketAddress;

@Service
public class LocalProxyService {
    private final Logger logger = LoggerFactory.getLogger(LoggerFactory.class);

    private final LocalProxyServerChannelInitializer initializer;
    private final AppConfigProperties properties;
    private final EventLoopGroup eventLoopGroup;
    private final Bootstrap bootstrap;
    private final ApplicationContext context;

    public LocalProxyService(EventLoopGroup eventLoopGroup, LocalProxyServerChannelInitializer initializer, AppConfigProperties properties, ApplicationContext context) {
        this.initializer = initializer;
        this.properties = properties;
        this.eventLoopGroup = eventLoopGroup;
        this.context = context;
        this.bootstrap = new Bootstrap();
    }

    public void start() {
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .remoteAddress(new InetSocketAddress(properties.getRemote().getHost(), properties.getRemote().getPort()))
                .handler(initializer);
        try {
            ChannelFuture future = bootstrap.connect().sync();
            future.addListener(f -> {
                if (f.isSuccess()) {
                    logger.info("连接远程代理服务成功！ Host:[{}] Port:[{}]", properties.getRemote().getHost(), properties.getRemote().getPort());
                } else {
                    logger.error("连接远程代理服务失败！");
                }
            });
            future.channel().closeFuture().sync();
        } catch (Exception e){
            logger.error("连接远程代理服务失败！msg: {}", e.getMessage());
            stop();
            SpringApplication.exit(context);
        }
    }

    public void stop() {
        try {
            eventLoopGroup.shutdownGracefully().sync();
        } catch (InterruptedException e) {
            logger.error("关闭连接远程代理服务失败！msg: {}", e.getMessage());
        }
    }
}
