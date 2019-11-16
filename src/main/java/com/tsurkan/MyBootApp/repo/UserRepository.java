package com.tsurkan.MyBootApp.repo;

import com.tsurkan.MyBootApp.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByLogin(String login);
}
