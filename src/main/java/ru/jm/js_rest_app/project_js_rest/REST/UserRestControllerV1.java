package ru.jm.js_rest_app.project_js_rest.REST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

import ru.jm.js_rest_app.project_js_rest.dto.RoleDTO;
import ru.jm.js_rest_app.project_js_rest.dto.UserDTO;
import ru.jm.js_rest_app.project_js_rest.mappers.RoleMapper;
import ru.jm.js_rest_app.project_js_rest.mappers.UserMapper;
import ru.jm.js_rest_app.project_js_rest.models.Role;
import ru.jm.js_rest_app.project_js_rest.models.User;
import ru.jm.js_rest_app.project_js_rest.service.UserService;

@RestController
@RequestMapping("/api/v1/admin/users/")
public class UserRestControllerV1 {

    @Autowired
    private UserService userService;

    @GetMapping(value = "{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("id") Long customerId) {
        if (customerId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User user = this.userService.getUser(customerId);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(UserMapper.INSTANCE.toDTO(user), HttpStatus.OK);
    }

    @GetMapping(value = "")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> users = this.userService.getAllUsers();
        List<UserDTO> userDTOs = users.stream().map(UserMapper.INSTANCE::toDTO).collect(Collectors.toList());
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userDTOs, HttpStatus.OK);
    }


    @PostMapping(value = "")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        HttpHeaders headers = new HttpHeaders();

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        this.userService.saveUser(user);
        return new ResponseEntity<>(user, headers, HttpStatus.CREATED);
    }

    @PutMapping(value = "")
    public ResponseEntity<User> updateUser(@RequestBody User user, UriComponentsBuilder builder) {
        HttpHeaders httpHeaders = new HttpHeaders();

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        this.userService.updateUser(user);

        return new ResponseEntity<>(user, httpHeaders, HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<User> deleteUser(@PathVariable("id") Integer id) {
        User user = this.userService.getUser(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        this.userService.removeUser(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/roles/")
    public ResponseEntity<List<RoleDTO>> getAllRoles() {
        List<Role> roles = this.userService.getRoles();
        List<RoleDTO> roleDTOs = roles.stream().map(RoleMapper.INSTANCE::toDTO).collect(Collectors.toList());

        if (roles.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(roleDTOs, HttpStatus.OK);
    }
}

