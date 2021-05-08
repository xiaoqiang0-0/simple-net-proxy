package org.example.simplenetproxy.server.management;

import org.example.simplenetproxy.core.auth.User;
import org.example.simplenetproxy.core.auth.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ManagementRestController {
    @Autowired
    private TransformManagement management;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ApplicationContext context;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.getAllUserList();
    }

    @GetMapping("/shutdown")
    public ResponseEntity<?> shutdown() {
        //TODO 停止服务

        return ResponseEntity.ok("感谢您的使用，再见！");
    }
}
