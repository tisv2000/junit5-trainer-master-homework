package com.dmdev.unit.mapper;

import com.dmdev.entity.Gender;
import com.dmdev.entity.Role;
import com.dmdev.entity.User;
import com.dmdev.mapper.UserMapper;
import com.dmdev.util.LocalDateFormatter;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserMapperTest {

    @Test
    void map() {
        var user = User.builder()
                .id(1)
                .name("Arwen")
                .birthday(LocalDateFormatter.format("1992-07-07"))
                .email("arwen@gmail.com")
                .password("999")
                .role(Role.USER)
                .gender(Gender.FEMALE)
                .build();

        var userDto = UserMapper.getInstance().map(user);

        assertThat(userDto.getId()).isEqualTo(user.getId());
        assertThat(userDto.getName()).isEqualTo(user.getName());
        assertThat(userDto.getBirthday()).isEqualTo(user.getBirthday());
        assertThat(userDto.getEmail()).isEqualTo(user.getEmail());
        assertThat(userDto.getRole()).isEqualTo(user.getRole());
        assertThat(userDto.getGender()).isEqualTo(user.getGender());
    }
}
