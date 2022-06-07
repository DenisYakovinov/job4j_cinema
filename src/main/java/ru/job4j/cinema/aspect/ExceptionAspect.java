package ru.job4j.cinema.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import ru.job4j.cinema.exception.*;
import ru.job4j.cinema.service.TicketServiceImpl;
import ru.job4j.cinema.service.UserServiceImpl;

@Aspect
@Component
public class ExceptionAspect {

    @Pointcut("execution(public * *(..))")
    private void allPublicMethods() {
    }

    @Pointcut("within(ru.job4j.cinema.service.*)")
    private void inServiceLayer() {
    }

    @Pointcut("execution(* ru.job4j.cinema.service.TicketServiceImpl.add(ru.job4j.cinema.model.Ticket))")
    private void toAddTicketMethod() {
    }

    @Pointcut("execution(* ru.job4j.cinema.service.UserServiceImpl.add(ru.job4j.cinema.model.User))")
    private void toAddUserMethod() {
    }

    @AfterThrowing(pointcut = "allPublicMethods() && inServiceLayer()", throwing = "exception")
    public void afterThrowing(JoinPoint joinPoint, DaoException exception) {
        throw new ServiceException(exception.getMessage(), exception);
    }

    @AfterThrowing(pointcut = "allPublicMethods() && toAddTicketMethod() || toAddUserMethod()", throwing = "exception")
    public void afterThrowing(JoinPoint joinPoint, UniqueViolationException exception) {
        if (joinPoint.getTarget() instanceof TicketServiceImpl) {
            throw new TicketBookedException(exception.getMessage(), exception);
        }
        if (joinPoint.getTarget() instanceof UserServiceImpl) {
            throw new EmailReservedException(exception.getMessage(), exception);
        }
    }
}