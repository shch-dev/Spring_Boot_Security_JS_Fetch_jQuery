package ru.jm.js_rest_app.project_js_rest.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.jm.js_rest_app.project_js_rest.dto.RoleDTO;
import ru.jm.js_rest_app.project_js_rest.models.Role;

@Mapper
public interface RoleMapper {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);
    RoleDTO toDTO(Role role);
}
