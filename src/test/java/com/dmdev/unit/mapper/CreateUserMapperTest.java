package com.dmdev.unit.mapper;

import com.dmdev.dto.CreateUserDto;
import com.dmdev.mapper.CreateUserMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CreateUserMapperTest {

    @Test
    void map() {
        var createUserDto = CreateUserDto.builder()
                .name("Arwen")
                .birthday("1992-07-07")
                .email("arwen@gmail.com")
                .password("999")
                .role("USER")
                .gender("FEMALE")
                .build();

        var userEntity = CreateUserMapper.getInstance().map(createUserDto);

        assertThat(userEntity.getName()).isEqualTo(createUserDto.getName());
        assertThat(userEntity.getBirthday()).isEqualTo(createUserDto.getBirthday());
        assertThat(userEntity.getEmail()).isEqualTo(createUserDto.getEmail());
        assertThat(userEntity.getPassword()).isEqualTo(createUserDto.getPassword());
        assertThat(userEntity.getRole().name()).isEqualTo(createUserDto.getRole());
        assertThat(userEntity.getGender().name()).isEqualTo(createUserDto.getGender());
    }
}
