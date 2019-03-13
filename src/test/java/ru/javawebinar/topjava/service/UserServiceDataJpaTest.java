package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.assertMatch;

@ActiveProfiles("datajpa")
public class UserServiceDataJpaTest extends UserServiceTest {

    @Test
    public void getWithMeals() throws Exception {
        User actual = service.get(ADMIN_ID);
        System.out.println(actual);
        actual.getMeals().forEach(System.out::println);
        ADMIN.setMeals(Arrays.asList(ADMIN_MEAL1, ADMIN_MEAL2));
        UserTestData.assertMatch(actual, ADMIN);
        MealTestData.assertMatch(actual.getMeals(), ADMIN_MEALS);
    }
}
