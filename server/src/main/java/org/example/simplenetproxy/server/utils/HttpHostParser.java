package org.example.simplenetproxy.server.utils;

import io.netty.buffer.ByteBuf;
import org.springframework.stereotype.Component;

import static io.netty.util.ByteProcessor.FIND_CRLF;

@Component
public class HttpHostParser implements HostParser {
    @Override
    public String parse(ByteBuf buf) {
        int hostLineStartIndex = buf.forEachByte(FIND_CRLF) + 2;
        int hostLienEndIndex = buf.forEachByte(hostLineStartIndex, buf.readableBytes(), FIND_CRLF);
        byte[] data = new byte[hostLienEndIndex - hostLineStartIndex];
        buf.getBytes(hostLineStartIndex, data);
        return new String(data).substring(6);
    }
}
