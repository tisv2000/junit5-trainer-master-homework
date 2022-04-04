package com.dmdev.integration.service;

import com.dmdev.dto.CreateUserDto;
import com.dmdev.exception.ValidationException;
import com.dmdev.integration.IntegrationTestBase;
import com.dmdev.service.UserService;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserServiceIT extends IntegrationTestBase {

    private static final UserService userService = UserService.getInstance();

    @Test
    void loginWithCorrectEmailAndPasswordReturnsUserDto() {
        // GIVEN
        var email = "vlad@gmail.com";
        var password = "456";

        // WHEN
        var result = userService.login(email, password);

        // THEN
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(4);
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForLoginWithIncorrectEmailOrPassword")
    void loginWithIncorrectEmailOrPasswordReturnsOptional(String email, String password) {
        // GIVEN
        // WHEN
        var result = userService.login(email, password);

        // THEN
        assertThat(result).isEmpty();
    }

    @Test
    void createUserReturnsUserDto() {
        // GIVEN
        var createUserDto = CreateUserDto.builder()
                .name("Arwen")
                .birthday("1992-07-07")
                .email("arwen@gmail.com")
                .password("999")
                .role("USER")
                .gender("FEMALE")
                .build();

        // WHEN
        var result = userService.create(createUserDto);

        // THEN
        assertThat(result.getId()).isGreaterThan(0);
        assertThat(result.getName()).isEqualTo(createUserDto.getName());
        assertThat(result.getBirthday()).isEqualTo(createUserDto.getBirthday());
        assertThat(result.getEmail()).isEqualTo(createUserDto.getEmail());
        assertThat(result.getRole().name()).isEqualTo(createUserDto.getRole());
        assertThat(result.getGender().name()).isEqualTo(createUserDto.getGender());
    }

    @Test
    @Tag("negative")
    void createUserThrowsExceptionIfValidationIsIncorrect() {
        // GIVEN
        var createUserDto = CreateUserDto.builder()
                .name("Arwen")
                .birthday("la-la-la")
                .email("arwen@gmail.com")
                .password("999")
                .role("USER")
                .gender("FEMALE")
                .build();

        // WHEN
        var exception = assertThrows(ValidationException.class, () -> userService.create(createUserDto));

        // THEN
        assertThat(exception.getErrors().size()).isGreaterThan(0);
    }

    static Stream<Arguments> getArgumentsForLoginWithIncorrectEmailOrPassword() {
        return Stream.of(
                Arguments.of("vlad@gmail.com", null),
                Arguments.of("", "456"),
                Arguments.of(null, "")
        );
    }
}
