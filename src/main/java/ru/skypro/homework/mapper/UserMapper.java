package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.dto.RegisterReq;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.Avatar;
import ru.skypro.homework.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    default UserDto userToUserDto(User user) {
        Avatar avatar = user.getAvatar();
        return userToUserDto(user, avatar == null ? null : avatar.getAvatarApi());
    }

    @Mapping(source = "avatarApi", target = "image")
    UserDto userToUserDto(User user, String avatarApi);

    @Mapping(source = "username", target = "email")
    User registerReqToUser(RegisterReq registerReq);
}
