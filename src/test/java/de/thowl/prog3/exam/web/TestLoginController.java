package de.thowl.prog3.exam.web;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;

import de.thowl.prog3.exam.service.impl.UserServiceImpl;
import de.thowl.prog3.exam.storage.entities.User;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class TestLoginController {

    private final String TEST_USER_NAME = "testuser";
    private final String TEST_PASSWORD = "test@example.com";

    @Autowired
    private UserServiceImpl userService;

    @Test
    @DisplayName("Should authenticate user without throwing exception")
    public void testLogin() {
        log.debug("Entering testLogin");

        assertDoesNotThrow(() -> {
            User user = userService.authenticate(TEST_USER_NAME, TEST_PASSWORD);
            assertNotNull(user, "User should not be null after successful authentication");
            assertEquals(TEST_USER_NAME, user.getName(), "Username should match");
        });
    }

    @Test
    @DisplayName("Should store authenticated user in session")
    public void testSessionStorage() {
        log.debug("Entering testSessionStorage");

        MockHttpSession session = new MockHttpSession();
        User user = userService.authenticate(TEST_USER_NAME, TEST_PASSWORD);

        assertNotNull(user, "User should not be null after authentication");
        session.setAttribute("user", user);

        User sessionUser = (User) session.getAttribute("user");
        assertNotNull(sessionUser, "Session should contain the authenticated user");
        assertEquals(TEST_USER_NAME, sessionUser.getName(), "Stored session user should match authenticated user");
    }
}

