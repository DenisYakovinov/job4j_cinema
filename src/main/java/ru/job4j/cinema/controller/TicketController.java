package ru.job4j.cinema.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.cinema.exception.TicketBookedException;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.SessionService;
import ru.job4j.cinema.service.TicketService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class TicketController {

    public static final Logger logger = LoggerFactory.getLogger(TicketController.class);

    private final TicketService ticketService;
    private final SessionService sessionService;

    public TicketController(TicketService ticketService, SessionService sessionService) {
        this.ticketService = ticketService;
        this.sessionService = sessionService;
    }

    @PostMapping("/buyTicket")
    public String buyTicket(Model model, @RequestParam(name = "seat", required = false) String seat,
                            @RequestParam("currentMovieSessionId") int sessionId,
                            HttpSession session) {
        if (seat == null) {
            return "redirect:/index?failSeatSelect=true";
        }
        int row = Character.getNumericValue(seat.charAt(0));
        int cell = Character.getNumericValue(seat.charAt(1));
        User user = (User) session.getAttribute("user");
        int userId = user.getId();
        Session movieSession = sessionService.findById(sessionId).get();
        ticketService.add(new Ticket(movieSession, row, cell, userId));
        List<Ticket> tickets = ticketService.findAllByUserId(userId);
        model.addAttribute("tickets", tickets);
        model.addAttribute("user", user);
        return "/account";
    }

    @ExceptionHandler(TicketBookedException.class)
    public String handleException(Model model, Exception e) {
        logger.error(e.getStackTrace().toString());
        return "redirect:/index?ticketAlreadyBooked=true";
    }
}
