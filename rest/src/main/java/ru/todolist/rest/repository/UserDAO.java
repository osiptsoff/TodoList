package ru.todolist.rest.repository;

import org.springframework.security.core.userdetails.UserDetailsService;

import ru.todolist.rest.model.User;

public interface UserDAO extends DAO<User>, UserDetailsService {
}
