package com.portfolio.blogging.repository;

import com.portfolio.blogging.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = NONE)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder().name("name").email("name@email.com").password("password").build();
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void testUserCreation() {
        User savedUser = userRepository.save(user);

        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isGreaterThan(0);
        assertThat(savedUser.getName()).isEqualTo("name");
        assertThat(savedUser.getEmail()).isEqualTo("name@email.com");
        assertThat(savedUser.getPassword()).isEqualTo("password");
    }

    @Test
    void testFindUserByID() {
        User savedUser = userRepository.save(user);

        User userByID = userRepository.findById(savedUser.getId()).get();

        assertThat(userByID).isNotNull();
        assertThat(userByID.getId()).isEqualTo(savedUser.getId());
        assertThat(userByID.getName()).isEqualTo(savedUser.getName());
        assertThat(userByID.getEmail()).isEqualTo(savedUser.getEmail());
        assertThat(userByID.getPassword()).isEqualTo(savedUser.getPassword());
    }

    @Test
    void testFindUserByEmail() {
        User savedUser = userRepository.save(user);

        User userByEmail = userRepository.findByEmail(savedUser.getEmail()).get();

        assertThat(userByEmail).isNotNull();
        assertThat(userByEmail.getId()).isEqualTo(savedUser.getId());
        assertThat(userByEmail.getName()).isEqualTo(savedUser.getName());
        assertThat(userByEmail.getEmail()).isEqualTo(savedUser.getEmail());
        assertThat(userByEmail.getPassword()).isEqualTo(savedUser.getPassword());
    }

    @Test
    void testFindAllUsers() {
        User savedUser = userRepository.save(user);

        List<User> usersList = userRepository.findAll();

        assertThat(usersList).isNotNull();
        assertEquals(1, usersList.size());
        assertThat(usersList.get(0).getId()).isEqualTo(savedUser.getId());
        assertThat(usersList.get(0).getName()).isEqualTo(savedUser.getName());
        assertThat(usersList.get(0).getEmail()).isEqualTo(savedUser.getEmail());
        assertThat(usersList.get(0).getPassword()).isEqualTo(savedUser.getPassword());
    }
}