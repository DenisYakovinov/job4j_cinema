package ru.job4j.cinema.aspect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.job4j.cinema.exception.ServiceException;

@ControllerAdvice
public class GlobalControllerExceptionAspect {

    public static final Logger logger = LoggerFactory.getLogger(GlobalControllerExceptionAspect.class);

    @ExceptionHandler(ServiceException.class)
    public String handleException(Model model, Exception e) {
        logger.error(e.getStackTrace().toString());
        model.addAttribute("error", e.getMessage());
        return "/baseError";
    }
}
