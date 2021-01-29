package ru.jm.js_rest_app.project_js_rest.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    private int id;
    private String username;
    private String surname;
    private String email;
    private String password;
    private List<RoleDTO> roles;
}
