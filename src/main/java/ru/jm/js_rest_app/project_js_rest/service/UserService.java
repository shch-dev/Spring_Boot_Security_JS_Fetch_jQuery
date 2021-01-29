package ru.jm.js_rest_app.project_js_rest.service;

import ru.jm.js_rest_app.project_js_rest.models.Role;
import ru.jm.js_rest_app.project_js_rest.models.User;

import java.util.List;

public interface UserService {

    public List<User> getAllUsers();

    public void saveUser(User user);

    public void removeUser(long id);

    public void updateUser(User user);

    public User getUser(long id);

    public List<Role> getRoles();
}
