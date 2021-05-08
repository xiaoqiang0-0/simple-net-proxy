package org.example.simplenetproxy.core.server;

public interface Server {

    int STATE_ERROR = -1;
    int STATE_ACTIVE = 1;
    int STATE_INACTIVE = 0;

    void start();

    default void stop() {
    }

    default int state() {
        return 0;
    }
}
