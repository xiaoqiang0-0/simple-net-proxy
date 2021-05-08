package org.example.simplenetproxy.server.auth;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.example.simplenetproxy.core.auth.AuthPacket;
import org.example.simplenetproxy.core.auth.AuthPacketDecoder;
import org.example.simplenetproxy.core.auth.User;
import org.example.simplenetproxy.core.auth.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.example.simplenetproxy.core.auth.AuthPacket.RESULT_FAILED;
import static org.example.simplenetproxy.core.auth.AuthPacket.RESULT_OK;

@Component
public class LocalAuthHandler extends SimpleChannelInboundHandler<AuthPacket> {
    private final Logger logger = LoggerFactory.getLogger(LocalAuthHandler.class);

    private static final AuthPacket AUTH_SUCCESS;

    private static AuthPacket getAuthFailedMsg(String msg) {
        AuthPacket p = new AuthPacket();
        p.setResult(RESULT_FAILED);
        p.setMessage(msg);
        return p;
    }

    static {
        AUTH_SUCCESS = new AuthPacket();
        AUTH_SUCCESS.setResult(RESULT_OK);
        AUTH_SUCCESS.setMessage("认证成功！");
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
