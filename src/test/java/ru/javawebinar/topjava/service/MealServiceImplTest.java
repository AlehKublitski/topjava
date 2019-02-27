package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceImplTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void getBetweenDates() {
    }

    @Test
    public void get() {
        Meal meal = service.get(100002, 100000);
        assertMatch(meal, MEAL1);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(100002, 100001);
    }

    @Test
    public void delete() {
        service.delete(100002, 100000);
        assertMatch(service.getAll(100000), MEAL2, MEAL3);
    }

    @Test(expected = NotFoundException.class)
    public void deletedNotFound() throws Exception {
        List<Meal> list = service.getAll(100000);
        service.delete(100002, 100009);
    }


    @Test
    public void getBetweenDateTimes() {
    }

    @Test
    public void getAll() {
        List<Meal> list = service.getAll(100000);
        assertMatch(list, MEAL2, MEAL1, MEAL3);
    }

    @Test
    public void update() {
        service.update(UPDATEMEAL, 100000);
        Meal meal = service.get(MEAL3_ID, 100000);
        assertMatch(meal, UPDATEMEAL);
    }

    @Test(expected = NotFoundException.class)
    public void updateNotFound() {
        service.update(UPDATEMEAL, 100007);
    }

    @Test
    public void create() {
        Meal newMeal = new Meal(LocalDateTime.parse("2016-12-12T12:12"), "ужин НОВЫЙ", 1500);
        service.create(newMeal, 100000);
        List<Meal> list = service.getAll(100000);
        assertMatch(list, MEAL2, MEAL1, MEAL3, newMeal);

    }
}