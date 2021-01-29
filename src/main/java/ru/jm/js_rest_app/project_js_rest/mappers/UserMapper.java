package ru.jm.js_rest_app.project_js_rest.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.jm.js_rest_app.project_js_rest.dto.UserDTO;
import ru.jm.js_rest_app.project_js_rest.models.User;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    UserDTO toDTO(User user);
}