package com.dmdev.unit.util;

import com.dmdev.util.LocalDateFormatter;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LocalDateFormatterTest {

    @Test
    void formatDateFromStringToLocalDate() {
        var stringDate = "2022-01-01";
        var localDate = LocalDateFormatter.format(stringDate);
        assertThat(localDate.getYear()).isEqualTo(2022);
    }

    @Test
    void isValidReturnsTrueWhenLocalDateCanBeParsedFromString() {
        var stringDate = "2022-01-01";
        var isValid = LocalDateFormatter.isValid(stringDate);
        assertThat(isValid).isTrue();
    }

    @Test
    @Tag("negative")
    void isValidReturnsFalseWhenLocalDateCanBeParsedFromString() {
        var stringDate = "01-01-2022";
        var isValid = LocalDateFormatter.isValid(stringDate);
        assertThat(isValid).isFalse();
    }
}
