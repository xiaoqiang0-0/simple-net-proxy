package org.example.simplenetproxy.client.auth;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.example.simplenetproxy.client.config.AppConfigProperties;
import org.example.simplenetproxy.client.proxy.ProxyHandler;
import org.example.simplenetproxy.client.server.LocalProxyService;
import org.example.simplenetproxy.core.auth.AuthPacket;
import org.example.simplenetproxy.core.auth.AuthPacketCodec;
import org.example.simplenetproxy.core.auth.AuthPacketDecoder;
import org.example.simplenetproxy.core.auth.AuthPacketEncoder;
import org.example.simplenetproxy.core.proxy.ProxyPacketDecoder;
import org.example.simplenetproxy.core.proxy.ProxyPacketEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import static org.example.simplenetproxy.core.auth.AuthPacket.RESULT_OK;

@Component
public class AuthHandler extends SimpleChannelInboundHandler<AuthPacket> {
    private final Logger logger = LoggerFactory.getLogger(AuthHandler.class);

    private final ApplicationContext context;
    private final AppConfigProperties properties;

    @Autowired
    private ProxyPacketDecoder proxyPacketDecoder;
    @Autowired
    private ProxyPacketEncoder proxyPacketEncoder;
    @Autowired
    private ProxyHandler proxyHandler;

    @Autowired
    private LocalProxyService localProxyService;

    public LocalProxyService getLocalProxyService() {
        return localProxyService;
    }

    public void setLocalProxyService(LocalProxyService localProxyService) {
        this.localProxyService = localProxyService;
    }

    public AuthHandler(ApplicationContext context, AppConfigProperties properties) {
        this.context = context;
        this.properties = properties;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        //TODO auth
        AuthPacket authPacket = new AuthPacket();
        authPacket.setUsername(properties.getAuth().getUsername());
        authPacket.setPassword(properties.getAuth().getPassword());
        authPacket.setResult(1);
        ctx.channel().writeAndFlush(authPacket);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, AuthPacket msg) throws Exception {
        if (msg.getResult() == RESULT_OK) {
            logger.info("远程服务连接成功，通过认证！");
            ctx.pipeline().remove(this);
            ctx.pipeline().remove(AuthPacketDecoder.class);
            ctx.pipeline().remove(AuthPacketEncoder.class);
            ctx.pipeline().addLast(proxyPacketDecoder);
            ctx.pipeline().addLast(proxyHandler);
            ctx.pipeline().addLast(proxyPacketEncoder);

        } else {
            logger.error("远程服务连接校验失败， msg: {}", msg.getMessage());
            //TODO exit
            localProxyService.stop();
            SpringApplication.exit(context);
        }
    }
}
