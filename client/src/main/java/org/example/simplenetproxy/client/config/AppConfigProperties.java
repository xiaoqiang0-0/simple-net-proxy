package org.example.simplenetproxy.client.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "proxy")
public class AppConfigProperties {
    private Address remote;
    private Address local;
    private AuthInfo auth;

    public Address getRemote() {
        return remote;
    }

    public void setRemote(Address remote) {
        this.remote = remote;
    }

    public Address getLocal() {
        return local;
    }

    public void setLocal(Address local) {
        this.local = local;
    }

    public AuthInfo getAuth() {
        return auth;
    }

    public void setAuth(AuthInfo auth) {
        this.auth = auth;
    }

    public static class Address {
        private String host;
        private int port;

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }
    }

    public static class AuthInfo {
        private String username;
        private String password;

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
    }
}
