package ru.job4j.cinema;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@Controller
public class CinemaStarter {

    @GetMapping("/")
    String home() {
        return "redirect:/index";
    }

    public static void main(String[] args) {

        SpringApplication.run(CinemaStarter.class, args);
    }
}
