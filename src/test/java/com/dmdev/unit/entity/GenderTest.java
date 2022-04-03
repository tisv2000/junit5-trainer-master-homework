package com.dmdev.unit.entity;

import com.dmdev.entity.Gender;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class GenderTest {

    @Test
    void findExistingGenre() {
        var maybeGender = Gender.find("MALE");
        assertThat(maybeGender.isPresent()).isTrue();
        assertThat(maybeGender.get()).isEqualTo(Gender.MALE);
    }

    @Test
    void findNotExistingGenreReturnsOptional() {
        var maybeGender = Gender.find("OTHER");
        assertThat(maybeGender).isEmpty();
    }
}
