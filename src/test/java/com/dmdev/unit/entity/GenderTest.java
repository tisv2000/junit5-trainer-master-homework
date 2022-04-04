package com.dmdev.unit.entity;

import com.dmdev.entity.Gender;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GenderTest {

    @Test
    void findExistingGenre() {
        // GIVEN
        // WHEN
        var maybeGender = Gender.find("MALE");

        // THEN
        assertThat(maybeGender.isPresent()).isTrue();
        assertThat(maybeGender.get()).isEqualTo(Gender.MALE);
    }

    @Test
    void findNotExistingGenreReturnsOptional() {
        // GIVEN
        // WHEN
        var maybeGender = Gender.find("OTHER");

        // THEN
        assertThat(maybeGender).isEmpty();
    }
}
