package ru.skypro.homework.mapper;

import org.junit.jupiter.api.Test;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.User;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static ru.skypro.homework.ConstantsTest.*;

class UserMapperTest {

    @Test
    void shouldMapUserToUserDto() {
        UserDto actual = UserMapper.INSTANCE.userToUserDto(USER_TEST);

        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(USER_ID);
        assertThat(actual.getEmail()).isEqualTo(EMAIL);
        assertThat(actual.getFirstName()).isEqualTo(FIRST_NAME);
        assertThat(actual.getLastName()).isEqualTo(LAST_NAME);
        assertThat(actual.getPhone()).isEqualTo(PHONE);
        assertThat(actual.getImage()).isEqualTo(USER_AVATAR_API_1);
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