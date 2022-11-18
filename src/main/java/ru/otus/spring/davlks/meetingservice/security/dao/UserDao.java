package ru.otus.spring.davlks.meetingservice.security.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.otus.spring.davlks.meetingservice.security.entity.User;

public interface UserDao extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    User findUserByUsername(String username);
}
