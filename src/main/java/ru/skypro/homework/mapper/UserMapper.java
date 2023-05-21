package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.constant.Constant;
import ru.skypro.homework.dto.RegisterReq;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.User;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "avatarApi", target = "image")
    UserDto userToUserDto(User user, String avatarApi);

    User userDtoToUser(UserDto userDto);

    @Mapping(source = "email", target = "username")
    RegisterReq userToRegisterReq(User user);

    @Mapping(source = "username", target = "email")
    User registerReqToUser(RegisterReq registerReq);

}
