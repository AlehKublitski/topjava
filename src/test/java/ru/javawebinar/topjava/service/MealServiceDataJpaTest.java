package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.model.Meal;

import static ru.javawebinar.topjava.MealTestData.assertMatch;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.assertMatch;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles({"datajpa"})
public class MealServiceDataJpaTest extends MealServiceTest {

    @Test
    public void getByIdWithUser() throws Exception {
        Meal actual = service.get(ADMIN_MEAL_ID, ADMIN_ID);
        System.out.println(actual);
        System.out.println(actual.getUser());
        assertMatch(actual, ADMIN_MEAL1);
        assertMatch(actual.getUser(), ADMIN);
    }
}
