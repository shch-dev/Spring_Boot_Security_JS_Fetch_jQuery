package ru.jm.js_rest_app.project_js_rest.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.jm.js_rest_app.project_js_rest.models.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    public User getUserByUsername(String username);
}
