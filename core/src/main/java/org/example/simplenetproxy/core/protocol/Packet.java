package org.example.simplenetproxy.core.protocol;

import io.netty.buffer.ByteBuf;

public class Packet {
    public static byte TYPE_AUTH = 1;
    public static byte TYPE_DATA = 2;
    public static byte TYPE_OTHER = 9;
    public static byte TYPE_ERROR = -1;

    public static byte STATE_OK = 1;
    public static byte STATE_ERROR = 0;

    public static int BODY_START_INDEX = 10;

    private int id;
    private byte type;
    private byte state;
    private int length;
    private ByteBuf body;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public byte getState() {
        return state;
    }

    public void setState(byte state) {
        this.state = state;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public ByteBuf getBody() {
        return body;
    }

    public void setBody(ByteBuf body) {
        this.body = body;
    }
}
