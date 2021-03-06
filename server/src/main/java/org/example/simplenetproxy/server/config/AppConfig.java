package org.example.simplenetproxy.server.config;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import org.example.simplenetproxy.core.auth.AuthPacketCodec;
import org.example.simplenetproxy.core.auth.AuthPacketDecoder;
import org.example.simplenetproxy.core.auth.AuthPacketEncoder;
import org.example.simplenetproxy.core.auth.UserRepository;
import org.example.simplenetproxy.core.protocol.PacketCodec;
import org.example.simplenetproxy.core.protocol.PacketDecoder;
import org.example.simplenetproxy.core.protocol.PacketEncoder;
import org.example.simplenetproxy.core.proxy.ProxyPacketCodec;
import org.example.simplenetproxy.core.proxy.ProxyPacketDecoder;
import org.example.simplenetproxy.core.proxy.ProxyPacketEncoder;
import org.example.simplenetproxy.server.auth.SimpleUserRepository;
import org.example.simplenetproxy.server.management.SimpleTransformManagement;
import org.example.simplenetproxy.server.management.TransformManagement;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;


@Configuration
public class AppConfig {
    @Bean
    @ConditionalOnMissingBean(UserRepository.class)
    public UserRepository userRepository() {
        return new SimpleUserRepository();
    }

    @Bean
    @ConditionalOnMissingBean(TransformManagement.class)
    public TransformManagement transformManagement() {
        return new SimpleTransformManagement();
    }

    @Bean
    public EventLoopGroup parentGroup() {
        return new NioEventLoopGroup();
    }

    @Bean
    public EventLoopGroup childGroup() {
        return new NioEventLoopGroup();
    }

    @Bean
    public PacketDecoder packetDecoder() {
        return new PacketDecoder();
    }

    @Bean
    public PacketEncoder packetEncoder() {
        return new PacketEncoder();
    }

    @Bean
    public AuthPacketEncoder authPacketEncoder() {
        return new AuthPacketEncoder();
    }

    @Bean
    public AuthPacketDecoder authPacketDecoder() {
        return new AuthPacketDecoder();
    }

    @Bean
    public ProxyPacketDecoder proxyPacketDecoder() {
        return new ProxyPacketDecoder();
    }

    @Bean
    public ProxyPacketEncoder proxyPacketEncoder() {
        return new ProxyPacketEncoder();
    }

    @Bean
    public PacketCodec packetCodec(PacketEncoder packetEncoder, PacketDecoder packetDecoder) {
        return new PacketCodec(packetDecoder, packetEncoder);
    }

    @Bean
    public AuthPacketCodec authPacketCodec(AuthPacketDecoder authPacketDecoder, AuthPacketEncoder authPacketEncoder) {
        return new AuthPacketCodec(authPacketDecoder, authPacketEncoder);
    }

    @Bean
    public ProxyPacketCodec proxyPacketCodec(ProxyPacketDecoder proxyPacketDecoder, ProxyPacketEncoder proxyPacketEncoder) {
        return new ProxyPacketCodec(proxyPacketDecoder, proxyPacketEncoder);
    }

    @Bean
    public Executor asyncServiceExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //?????????????????????
        executor.setCorePoolSize(5);
        //?????????????????????
        executor.setMaxPoolSize(5);
        //??????????????????
        executor.setQueueCapacity(99999);
        //??????????????????????????????????????????
        executor.setThreadNamePrefix("async-service-");

        // rejection-policy??????pool????????????max size?????????????????????????????????
        // CALLER_RUNS??????????????????????????????????????????????????????????????????????????????
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //???????????????
        executor.initialize();
        return executor;
    }
}
