package org.example.simplenetproxy.core.auth;

import java.util.List;

public interface UserRepository {
    User getUserInfoByDomain(String domain);

    List<User> getAllUserList();

    User getUserById(Integer id);

    User getUserByUsername(String username);
}
