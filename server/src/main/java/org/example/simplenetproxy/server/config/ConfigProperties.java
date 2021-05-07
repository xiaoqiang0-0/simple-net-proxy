package org.example.simplenetproxy.server.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "net.proxy")
public class ConfigProperties {
    private int localPort;
    private int outerPort;

    public int getLocalPort() {
        return localPort;
    }

    public void setLocalPort(int localPort) {
        this.localPort = localPort;
    }

    public int getOuterPort() {
        return outerPort;
    }

    public void setOuterPort(int outerPort) {
        this.outerPort = outerPort;
    }
}
