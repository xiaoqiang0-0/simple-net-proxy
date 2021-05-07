package org.example.simplenetproxy.server.utils;

import io.netty.buffer.ByteBuf;
import org.springframework.stereotype.Component;

public interface HostParser {
    String parse(ByteBuf buf);
}
