package org.example.simplenetproxy.server.server;

public interface Server {
    void start();

    default void stop() {
    }
}
