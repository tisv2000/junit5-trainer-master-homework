package com.dmdev.integration.dao;

import com.dmdev.dao.UserDao;
import com.dmdev.entity.Gender;
import com.dmdev.entity.Role;
import com.dmdev.entity.User;
import com.dmdev.integration.IntegrationTestBase;
import com.dmdev.util.LocalDateFormatter;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserDaoIT extends IntegrationTestBase {

    private static final UserDao underTest = UserDao.getInstance();

    @Test
    void findAllUsersReturnsNotEmptyListIfNotEmpty() {
        // GIVEN
        // WHEN
        var users = underTest.findAll();

        // THEN
        assertThat(users).isNotEmpty();
    }

    @Test
    void findByExistingIdReturnsUser() {
        // GIVEN
        // WHEN
        var user = underTest.findById(1);

        // THEN
        assertThat(user).isPresent();
        assertThat(user.get().getEmail()).isEqualTo("ivan@gmail.com");
    }

    @Test
    void findByExistingEmailAndPasswordReturnsUser() {
        // GIVEN
        var email = "petr@gmail.com";
        var password = "123";
        var expectedName = "Petr";

        // WHEN
        var user = underTest.findByEmailAndPassword(email, password);

        // THEN
        assertThat(user).isPresent();
        assertThat(user.get().getName()).isEqualTo(expectedName);
    }

    @Test
    void saveUserInDataBase() {
        // GIVEN
        var newName = "Frodo";
        var newUser = User
                .builder()
                .name(newName)
                .birthday(LocalDateFormatter.format("1995-05-05"))
                .email("frodo@gmail.com")
                .password("888")
                .role(Role.USER)
                .gender(Gender.MALE)
                .build();

        // WHEN
        var savedUser = underTest.save(newUser);

        // THEN
        assertThat(savedUser.getId()).isGreaterThan(1);
        assertThat(savedUser.getName()).isEqualTo(newName);
    }

    @Test
    void deleteUserById() {
        // GIVEN
        // WHEN
        var isUserDeleted = underTest.delete(1);

        // THEN
        assertThat(isUserDeleted).isTrue();
        assertThat(underTest.findById(1)).isEmpty();
    }

    @Test
    void updateUser() {
        // GIVEN
        var user = underTest.findById(1).get();
        var newName = "John";
        user.setName(newName);

        // WHEN
        underTest.update(user);

        // THEN
        assertThat(underTest.findById(1).get().getName()).isEqualTo(newName);
    }
}
