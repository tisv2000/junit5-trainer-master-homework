package com.dmdev.unit.entity;

import com.dmdev.entity.Role;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RoleTest {

    @Test
    void findExistingRole() {
        var maybeRole = Role.find("USER");
        assertThat(maybeRole.isPresent()).isTrue();
        assertThat(maybeRole.get()).isEqualTo(Role.USER);
    }

    @Test
    void findNotExistingRoleReturnsOptional() {
        var maybeGender = Role.find("OTHER");
        assertThat(maybeGender).isEmpty();
    }
}
