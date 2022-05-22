# Cinema tickets booking spring boot app
Service for booking tickets to the cinema.

### 1. The user sees available and already booked sessions

![mainPage](images_examples/mainPage.png)

to be able to book a ticket, you need to register and log in
(if the email address is already reserved, the user sees an error message)
![registration](images_examples/registration.png)

after that, the user needs to log
in (if the password is incorrect, the user sees an error)

![login](images_examples/login.png)

### 2. Booking tickets::

After authorization, the confirm button is available to the user,
which books tickets for the selected session and redirects to the user's personal account
(if two users try to book the same ticket
at the same time, the one who first clicks the "Confirm" button will book,
the second user will receive an error message that the ticket has already been booked)
![mainWithBookButtonFoUser](images_examples/mainWithBookButtonFoUser.png)

If everything was successful, the user will be redirected to his personal account, which contains his data
and information about all tickets booked by him.

![account](images_examples/account.png)

The user can repeat the process to book more tickets by returning to Main
<br>
<h2>Technologies</h2>
<ul>
    <li>Spring boot</li>
    <li>Spring MVC</li>
    <li>Spring AOP</li>
    <li>Bootstrap</li>
    <li>Thymeleaf</li>
    <li>Postgres</li>
    <Li>liquibase(for db migration)</Li>> 
    <li>Junit</li>
    <li>Mockito</li>
</ul>
