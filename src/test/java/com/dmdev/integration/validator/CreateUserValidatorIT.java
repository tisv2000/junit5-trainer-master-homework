package com.dmdev.integration.validator;

import com.dmdev.dto.CreateUserDto;
import com.dmdev.validator.CreateUserValidator;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class CreateUserValidatorIT {

    private static final CreateUserValidator createUserValidator = CreateUserValidator.getInstance();

    @Test
    void validateCreateUserDtoWithCorrectData() {
        // GIVEN
        var correctUserDto = userDtoBuilder().build();

        // WHEN
        var validationResult = createUserValidator.validate(correctUserDto);

        // THEN
        assertThat(validationResult.isValid()).isTrue();
    }

    @ParameterizedTest
    @Tag("negative")
    @MethodSource("getArgumentsForValidateWithIncorrectDataTest")
    void validateWithIncorrectDataReturnsValidationResultWithError(
            CreateUserDto createUserDto, String expectedErrorCode, String expectedMessage
    ) {
        // GIVEN
        // WHEN
        var validationResult = createUserValidator.validate(createUserDto);

        // THEN
        assertThat(validationResult.isValid()).isFalse();
        assertThat(validationResult.getErrors().get(0).getCode()).isEqualTo(expectedErrorCode);
        assertThat(validationResult.getErrors().get(0).getMessage()).isEqualTo(expectedMessage);
    }

    static Stream<Arguments> getArgumentsForValidateWithIncorrectDataTest() {
        return Stream.of(
                Arguments.of(userDtoBuilder().birthday(null).build(), "invalid.birthday", "Birthday is invalid"),
                Arguments.of(userDtoBuilder().birthday("").build(), "invalid.birthday", "Birthday is invalid"),
                Arguments.of(userDtoBuilder().birthday("01-01-2000").build(), "invalid.birthday", "Birthday is invalid"),

                Arguments.of(userDtoBuilder().gender(null).build(), "invalid.gender", "Gender is invalid"),
                Arguments.of(userDtoBuilder().gender("").build(), "invalid.gender", "Gender is invalid"),
                Arguments.of(userDtoBuilder().gender("dummy").build(), "invalid.gender", "Gender is invalid"),

                Arguments.of(userDtoBuilder().role(null).build(), "invalid.role", "Role is invalid"),
                Arguments.of(userDtoBuilder().role("").build(), "invalid.role", "Role is invalid"),
                Arguments.of(userDtoBuilder().role("dummy").build(), "invalid.role", "Role is invalid")
        );
    }

    private static CreateUserDto.CreateUserDtoBuilder userDtoBuilder() {
        return CreateUserDto.builder()
                .name("Arwen")
                .birthday("1992-07-07")
                .email("arwen@gmail.com")
                .password("999")
                .role("USER")
                .gender("FEMALE");
    }
}
