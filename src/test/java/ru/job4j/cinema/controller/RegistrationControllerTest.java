package ru.job4j.cinema.controller;

import org.junit.jupiter.api.Test;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.UserService;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import static org.mockito.Mockito.*;

class RegistrationControllerTest {

    @Test
    void whenRegisterThenReturnPageString() {
        User user = new User(1, "user", "uaser@email.com", "+1-222-33-3-4", "111");
        UserService userService = mock(UserService.class);
        when(userService.add(user)).thenReturn(Optional.of(user));
        RegistrationController regController = new RegistrationController(userService);
        String expected = "redirect:/loginPage";
        assertEquals(expected, regController.register(user, "111"));
    }
}
