package org.example.simplenetproxy.server.auth;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.simplenetproxy.core.auth.User;
import org.example.simplenetproxy.core.auth.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;


import javax.annotation.PostConstruct;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleUserRepository implements UserRepository {
    private final Logger logger = LoggerFactory.getLogger(SimpleUserRepository.class);

    @Value("${usersLocal}")
    private String usersFilePath;

    public final Map<String, User> domainUserCacheMap;
    public final Map<String, User> usernameCacheMap;

    public SimpleUserRepository() {
        this.domainUserCacheMap = new ConcurrentHashMap<>();
        this.usernameCacheMap = new ConcurrentHashMap<>();
    }

    @PostConstruct
    private void init() {
        File file = new File(usersFilePath);
        try (FileInputStream inputStream = new FileInputStream(file)) {
            List<User> users = new ObjectMapper().readValue(file, new TypeReference<>() {
            });
            for (User user : users) {
                domainUserCacheMap.put(user.getDomain(), user);
                usernameCacheMap.put(user.getUsername(), user);
            }
        } catch (Exception e) {
            logger.error("用户列表json文件不存在或格式不正确，读取失败，请检查配置文件地址和文件内容！");
            System.exit(-1);
        }
    }

    public User getUserInfoByDomain(String domain) {
        return domainUserCacheMap.get(domain);
    }

    public List<User> getAllUserList() {
        return new ArrayList<>(usernameCacheMap.values());
    }

    public User getUserById(Integer id) {
        return null;
    }

    public User getUserByUsername(String username) {
        return usernameCacheMap.get(username);
    }
}
