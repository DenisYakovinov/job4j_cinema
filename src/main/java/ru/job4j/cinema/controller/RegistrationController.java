package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.cinema.exception.EmailReservedException;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.UserService;

@Controller
@ThreadSafe
public class RegistrationController {

    public static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user, @RequestParam(name = "repeatPassword") String repeatPassword) {
        if (!user.getPassword().equals(repeatPassword)) {
            return "redirect:/registration?failPass=true";
        }
        userService.add(user);
        return "redirect:/loginPage";
    }

    @GetMapping("/registration")
    public String registration(Model model, @RequestParam(name = "fail", required = false) Boolean fail,
                               @RequestParam(name = "failPass", required = false) Boolean failPass) {
        model.addAttribute("fail", fail != null);
        model.addAttribute("failPass", failPass != null);
        return "/registration";
    }

    @ExceptionHandler(EmailReservedException.class)
    public String handleException(Exception e) {
        logger.error(e.getStackTrace().toString());
        return "redirect:/registration?fail=true";
    }
}
