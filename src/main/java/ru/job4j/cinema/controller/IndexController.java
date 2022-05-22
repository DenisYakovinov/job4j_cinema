package ru.job4j.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.SessionService;
import ru.job4j.cinema.service.TicketService;
import ru.job4j.cinema.service.UserService;
import ru.job4j.cinema.util.SessionUtil;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class IndexController {

    private final UserService userService;
    private final TicketService ticketService;
    private final SessionService sessionService;

    public IndexController(UserService userService, TicketService ticketService, SessionService sessionService) {
        this.userService = userService;
        this.ticketService = ticketService;
        this.sessionService = sessionService;
    }

    @GetMapping("/index")
    public String index(Model model, HttpSession session,
                        @RequestParam(name = "failSeatSelect", required = false) Boolean failSeatSelect,
                        @RequestParam(name = "ticketAlreadyBooked", required = false) Boolean ticketAlreadyBooked) {
        List<Session> movieSessions = sessionService.findAll();
        if (movieSessions.size() == 0) {
            return "emptyMain";
        }
        User user = SessionUtil.getUserFromSession(session);
        model.addAttribute("user", user);
        model.addAttribute("tickets", ticketService.createListToView(1));
        model.addAttribute("currentMovieSession", sessionService.findById(1).orElse(new Session(0, "-")));
        model.addAttribute("movieSessions", movieSessions);
        model.addAttribute("failSeatSelect", failSeatSelect != null);
        model.addAttribute("ticketAlreadyBooked", ticketAlreadyBooked != null);
        return "index";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
