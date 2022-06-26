package ru.job4j.cinema.controller;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.UserService;
import ru.job4j.cinema.service.UserServiceImpl;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import static org.mockito.Mockito.*;

class RegistrationControllerTest {

    @Test
    void whenRegisterThenReturnPageString() {
        User user = new User(1, "user", "uaser@email.com", "+1-222-33-3-4", "111");
        UserService userService = mock(UserServiceImpl.class);
        when(userService.add(user)).thenReturn(Optional.of(user));
        RegistrationController regController = new RegistrationController(userService);
        String expected = "redirect:/loginPage";
        assertEquals(expected, regController.register(user, "111"));
    }

    @Test
    void whenRegistrationThenReturnRegistrationPageString() {
        UserService userService = mock(UserServiceImpl.class);
        RegistrationController regController = new RegistrationController(userService);
        Model model = mock(Model.class);
        String page = regController.registration(model, true, true);
        verify(model).addAttribute("fail", true);
        verify(model).addAttribute("failPass", true);
        assertEquals("/registration", page);
    }
}
