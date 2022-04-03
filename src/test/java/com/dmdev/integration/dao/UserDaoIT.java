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

    static UserDao underTest = UserDao.getInstance();

    @Test
    void findAllUsersReturnsNotEmptyListIfNotEmpty() {
        var users = underTest.findAll();
        assertThat(users).isNotEmpty();
    }

    @Test
    void findByExistingIdReturnsUser() {
        var user = underTest.findById(1);
        assertThat(user).isPresent();
        assertThat(user.get().getEmail()).isEqualTo("ivan@gmail.com");
    }

    @Test
    void findByExistingEmailAndPasswordReturnsUser() {
        var user = underTest.findByEmailAndPassword("petr@gmail.com", "123");
        assertThat(user).isPresent();;
        assertThat(user.get().getName()).isEqualTo("Petr");
    }

    @Test
    void saveUserInDataBase() {
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

        var savedUser = underTest.save(newUser);
        assertThat(savedUser.getId()).isGreaterThan(1);
        assertThat(savedUser.getName()).isEqualTo(newName);
    }

    @Test
    void deleteUserById() {
        var isUserDeleted = underTest.delete(1);
        assertThat(isUserDeleted).isTrue();
        assertThat(underTest.findById(1)).isEmpty();
    }

    @Test
    void updateUser() {
        var user = underTest.findById(1).get();
        var newName = "John";
        user.setName(newName);
        underTest.update(user);
        assertThat(underTest.findById(1).get().getName()).isEqualTo(newName);
    }

}
