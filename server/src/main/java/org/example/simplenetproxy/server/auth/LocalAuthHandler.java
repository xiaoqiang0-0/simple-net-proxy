package org.example.simplenetproxy.server.auth;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.example.simplenetproxy.core.auth.AuthPacket;
import org.example.simplenetproxy.core.auth.AuthPacketDecoder;
import org.example.simplenetproxy.core.auth.User;
import org.example.simplenetproxy.core.auth.UserRepository;
import org.example.simplenetproxy.core.protocol.Packet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.example.simplenetproxy.core.protocol.Packet.*;

@Component
public class LocalAuthHandler extends SimpleChannelInboundHandler<AuthPacket> {
    private final Logger logger = LoggerFactory.getLogger(LocalAuthHandler.class);

    private static final Packet AUTH_SUCCESS;

    private static Packet getAuthFailedMsg(String msg) {
        Packet p = new Packet();
        p.setType(TYPE_AUTH);
        p.setState(STATE_ERROR);
        p.setBody(Unpooled.wrappedBuffer(msg.getBytes()));
        p.setLength(p.getBody().readableBytes());
        return p;
    }

    static {
        AUTH_SUCCESS = new Packet();
        AUTH_SUCCESS.setType(TYPE_AUTH);
        AUTH_SUCCESS.setState(STATE_OK);
        AUTH_SUCCESS.setBody(Unpooled.wrappedBuffer("认证成功！".getBytes()));
        AUTH_SUCCESS.setLength(AUTH_SUCCESS.getBody().readableBytes());
    }

    @Autowired
    private UserRepository repository;

    protected void channelRead0(ChannelHandlerContext channelHandlerContext, AuthPacket authPacket) throws Exception {
        User user = repository.getUserByUsername(authPacket.getUsername());
        if (!user.getPassword().equals(authPacket.getPassword())) {
            throw new RuntimeException("认证失败，请检查用户名和密码是否正确！");
        }
        //TODO 账户已连接校验处理

        channelHandlerContext.pipeline().remove(this);
        channelHandlerContext.pipeline().remove(AuthPacketDecoder.class);
        channelHandlerContext.channel().writeAndFlush(AUTH_SUCCESS);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("客户端认证异常：远程客户端[{}], 错误消息:{}", ctx.channel().remoteAddress(), cause.getMessage());
        ctx.channel().writeAndFlush(getAuthFailedMsg(cause.getMessage()));
        ctx.channel().close();
    }
}
