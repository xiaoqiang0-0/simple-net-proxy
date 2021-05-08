package org.example.simplenetproxy.core.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.StandardCharsets;

public class AuthPacket {
    public static int RESULT_OK = 1;
    public static int RESULT_FAILED = -1;
    private String username;
    private String password;
    private int result;
    private String message;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ByteBuf toByteBuf() throws JsonProcessingException {
        return Unpooled.wrappedBuffer(new ObjectMapper().writeValueAsString(this).getBytes(StandardCharsets.UTF_8));
    }
}
