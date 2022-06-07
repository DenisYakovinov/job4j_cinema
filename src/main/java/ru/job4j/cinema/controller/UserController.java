package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.TicketService;
import ru.job4j.cinema.service.TicketServiceImpl;
import ru.job4j.cinema.service.UserService;
import ru.job4j.cinema.service.UserServiceImpl;
import ru.job4j.cinema.util.SessionUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@ThreadSafe
public class UserController {

    private final UserService userService;
    private final TicketService ticketService;

    public UserController(UserService userService, TicketService ticketService) {
        this.userService = userService;
        this.ticketService = ticketService;
    }

    @GetMapping("/loginPage")
    public String loginPage(Model model, @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("fail", fail != null);
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpServletRequest req) {
        Optional<User> userDb = userService.findUserByEmailAndPwd(
                user.getEmail(), user.getPassword()
        );
        if (userDb.isEmpty()) {
            return "redirect:/loginPage?fail=true";
        }
        HttpSession session = req.getSession();
        session.setAttribute("user", userDb.get());
        return "redirect:/index";
    }

    @GetMapping("/guestLogin")
    public String guestLogin(Model model, HttpSession session) {
        User user = SessionUtil.getUserFromSession(session);
        session.setAttribute("user", user);
        model.addAttribute("user", user);
        return "index";
    }

    @GetMapping("/toUserAccount")
    public String toUserAccount(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        int userId = user.getId();
        List<Ticket> tickets = ticketService.findAllByUserId(userId);
        model.addAttribute("tickets", tickets);
        model.addAttribute("user", user);
        return "/account";
    }
}
