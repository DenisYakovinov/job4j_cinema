package ru.job4j.cinema.persistence;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.job4j.cinema.config.TestDataSource;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.persistence.mapper.SessionMapper;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestDataSource.class)
class SessionDaoImplTest {

    @Autowired
    BasicDataSource dataSource;

    @Test
    void whenAddSession() {
        SessionDaoImpl sessionDao = new SessionDaoImpl(dataSource, new SessionMapper());
        Session session = new Session(1, "imaginary movie");
        sessionDao.add(session);
        Session sessionInDb = sessionDao.findById(session.getId()).get();
        assertEquals(session, sessionInDb);
    }
}
