package ru.javawebinar.topjava.web.meal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.TestUtil;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.SecurityUtil;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

public class MealRestControllerTest extends AbstractControllerTest {

    @Autowired
    protected MealService mealService;

    private static final String REST_URL = MealRestController.REST_URL + '/';

    @Test
    void testGet() throws Exception {
        SecurityUtil.setAuthUserId(ADMIN_ID);
        mockMvc.perform(get(REST_URL + ADMIN_MEAL_ID))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(ADMIN_MEAL1));
    }

    @Test
    void testDelete() throws Exception {
        SecurityUtil.setAuthUserId(ADMIN_ID);
        mockMvc.perform(delete(REST_URL + ADMIN_MEAL_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        MealTestData.assertMatch(mealService.getAll(ADMIN_ID), ADMIN_MEAL2);
    }

    @Test
    void testGetAll() throws Exception {
        SecurityUtil.setAuthUserId(ADMIN_ID);
        mockMvc.perform(get(REST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MealsUtil.getWithExcess(Arrays.asList(ADMIN_MEAL2, ADMIN_MEAL1), 2000)));
    }

    @Test
    void testGetBetween() throws Exception {
        SecurityUtil.setAuthUserId(ADMIN_ID);
        String startDate = "2015-06-01";
        String endDate = "2015-06-01";
        String startTime = "15:00";
        String endTime = "22:00";
        mockMvc.perform(get(String.format("%sfilter?startDate=%s&endDate=%s&startTime=%s&endTime=%s", REST_URL, startDate, endDate, startTime, endTime)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(Arrays.asList(MealsUtil.createWithExcess(ADMIN_MEAL2, true))));
    }

    @Test
    void testUpdate() throws Exception {
        SecurityUtil.setAuthUserId(USER_ID);
        int userId = SecurityUtil.authUserId();
        Meal updated = new Meal(MEAL1.getId(), MEAL1.getDateTime(), MEAL1.getDescription(), MEAL1.getCalories());
        updated.setDescription("Updated");
        updated.setCalories(222);
        mockMvc.perform(put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());
        MealTestData.assertMatch(mealService.get(MEAL1_ID, userId), updated);
    }

    @Test
    void testCreate() throws Exception {
        SecurityUtil.setAuthUserId(USER_ID);
        int userId = SecurityUtil.authUserId();
        Meal created = new Meal(null, LocalDateTime.now(), "новая еда", 555);

        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(created)))
                .andExpect(status().isCreated());
        Meal actual = TestUtil.readFromJson(action, Meal.class);
        created.setId(actual.getId());

        MealTestData.assertMatch(actual, created);
        MealTestData.assertMatch(mealService.getAll(userId), actual, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1);
    }

}
