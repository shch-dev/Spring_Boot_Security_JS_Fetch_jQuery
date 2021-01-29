package ru.jm.js_rest_app.project_js_rest.dao;

import org.springframework.data.repository.CrudRepository;
import ru.jm.js_rest_app.project_js_rest.models.Role;

public interface RoleRepository extends CrudRepository<Role, Integer> {
}
