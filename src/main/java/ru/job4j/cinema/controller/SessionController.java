package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.SessionService;
import ru.job4j.cinema.service.TicketService;
import ru.job4j.cinema.util.SessionUtil;

import javax.servlet.http.HttpSession;

@Controller
@ThreadSafe
public class SessionController {

    private final TicketService ticketService;
    private final SessionService sessionService;

    public SessionController(TicketService ticketService, SessionService sessionService) {
        this.ticketService = ticketService;
        this.sessionService = sessionService;
    }

    @GetMapping("/chooseSession/{sessionId}")
    public String index(Model model, @PathVariable("sessionId") int sessionId, HttpSession session) {
        User user = SessionUtil.getUserFromSession(session);
        model.addAttribute("user", user);
        model.addAttribute("tickets", ticketService.createListToView(sessionId));
        model.addAttribute("currentMovieSession", sessionService.findById(sessionId).orElse(new Session(0, "-")));
        model.addAttribute("movieSessions", sessionService.findAll());
        return "index";
    }
}
