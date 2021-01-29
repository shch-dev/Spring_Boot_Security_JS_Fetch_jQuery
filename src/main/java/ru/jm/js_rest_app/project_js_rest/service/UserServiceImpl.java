package ru.jm.js_rest_app.project_js_rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.jm.js_rest_app.project_js_rest.dao.RoleRepository;
import ru.jm.js_rest_app.project_js_rest.dao.UserRepository;
import ru.jm.js_rest_app.project_js_rest.models.Role;
import ru.jm.js_rest_app.project_js_rest.models.User;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void removeUser(long id) {
        userRepository.deleteById((int) id);
    }

    @Override
    public void updateUser(User user) {
        String oldPassword = userRepository.findById(user.getId()).orElse(null).getPassword();
        if (!oldPassword.equals(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userRepository.save(user);
    }

    @Override
    public User getUser(long id) {
        User user = null;

        Optional<User> optionalUser = userRepository.findById((int) id);
        if (optionalUser.isPresent()) { //если в optional присутствует User
            user = optionalUser.get(); //назначаю переменной user этого User
        } else {
            System.out.println("Optional does not contain a user"); //если Optional пустой, то вывожу в консоль ошибку
        }

        return user;
    }

    @Override
    public List<Role> getRoles() {
        List<Role> roles = (List<Role>) roleRepository.findAll();
        return roles;
    }
}
