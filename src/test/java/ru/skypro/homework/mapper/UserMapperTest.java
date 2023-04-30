package ru.skypro.homework.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.skypro.homework.dto.RegisterReq;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.User;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UserMapperTest {
    private final Integer ID = 1;
    private final String EMAIL = "user@gmail.com";
    private final String FIRST_NAME = "first name";
    private final String LAST_NAME = "last name";
    private final String PHONE = "+79876543211";
    private final String IMAGE = "image path";
    private final String PASSWORD = "password";
    private final Role ROLE = Role.USER;
    private final User USER_TEST = new User(ID, EMAIL, FIRST_NAME, LAST_NAME, PHONE, IMAGE, PASSWORD, ROLE);
    private final UserDto USER_DTO_TEST = new UserDto();
    private final RegisterReq REGISTER_REQ_TEST = new RegisterReq();

    {
        USER_DTO_TEST.setId(ID);
        USER_DTO_TEST.setEmail(EMAIL);
        USER_DTO_TEST.setFirstName(FIRST_NAME);
        USER_DTO_TEST.setLastName(LAST_NAME);
        USER_DTO_TEST.setPhone(PHONE);
        USER_DTO_TEST.setImage(IMAGE);

        REGISTER_REQ_TEST.setUsername(EMAIL);
        REGISTER_REQ_TEST.setPassword(PASSWORD);
        REGISTER_REQ_TEST.setFirstName(FIRST_NAME);
        REGISTER_REQ_TEST.setLastName(LAST_NAME);
        REGISTER_REQ_TEST.setPhone(PHONE);
        REGISTER_REQ_TEST.setRole(ROLE);
    }

    @Test
    void shouldMapUserToUserDto() {
        UserDto actual = UserMapper.INSTANCE.userToUserDto(USER_TEST);

        Assertions.assertEquals(USER_DTO_TEST, actual);
    }

    @Test
    void shouldMapUserDtoToUser() {
        User actual = UserMapper.INSTANCE.userDtoToUser(USER_DTO_TEST);

        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(ID);
        assertThat(actual.getEmail()).isEqualTo(EMAIL);
        assertThat(actual.getFirstName()).isEqualTo(FIRST_NAME);
        assertThat(actual.getLastName()).isEqualTo(LAST_NAME);
        assertThat(actual.getPhone()).isEqualTo(PHONE);
        assertThat(actual.getImage()).isEqualTo(IMAGE);
    }

    @Test
    void userToRegisterReq() {
        RegisterReq actual = UserMapper.INSTANCE.userToRegisterReq(USER_TEST);

        Assertions.assertEquals(REGISTER_REQ_TEST, actual);
    }

    @Test
    void shouldMapRegisterReqToUser() {
        User actual = UserMapper.INSTANCE.registerReqToUser(REGISTER_REQ_TEST);

        assertThat(actual).isNotNull();
        assertThat(actual.getEmail()).isEqualTo(EMAIL);
        assertThat(actual.getPassword()).isEqualTo(PASSWORD);
        assertThat(actual.getFirstName()).isEqualTo(FIRST_NAME);
        assertThat(actual.getLastName()).isEqualTo(LAST_NAME);
        assertThat(actual.getPhone()).isEqualTo(PHONE);
        assertThat(actual.getRole()).isEqualTo(ROLE);
    }
}