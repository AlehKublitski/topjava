package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;
    public static LocalDate startDateLocal = LocalDate.MIN;
    public static LocalDate endDateLocal = LocalDate.MAX;
    private static LocalTime startTimeLocal = LocalTime.MIN;
    private static LocalTime endTimeLocal = LocalTime.MAX;

    public List<MealTo> getAll() {
        log.info("getAll");
        return MealsUtil.getWithExcess(service.getAll(authUserId()), MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    public List<MealTo> getFiltred(String startTime, String endTime, String startDate, String endDate) {
        if (startTime != null && !startTime.isEmpty()) startTimeLocal = LocalTime.parse(startTime);
        if (endTime != null && !endTime.isEmpty()) endTimeLocal = LocalTime.parse(endTime);
        if (startDate != null && !startDate.isEmpty()) startDateLocal = LocalDate.parse(startDate);
        if (endDate != null && !endDate.isEmpty()) endDateLocal = LocalDate.parse(endDate);
        return MealsUtil.getFilteredWithExcess(service.getAll(authUserId()), MealsUtil.DEFAULT_CALORIES_PER_DAY, startTimeLocal, endTimeLocal, startDateLocal, endDateLocal);
    }

    public Meal get(int id) {
        log.info("get {}", id);
        System.out.println("id = " + id);
        System.out.println("userId = " + authUserId());
        return service.get(id, authUserId());
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        checkNew(meal);
        return service.create(meal, authUserId());
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id, authUserId());
    }

    public void update(Meal meal, int id) {
        log.info("update {} with id={}", meal, meal.getId());
        assureIdConsistent(meal, id);
        service.update(meal, authUserId());
    }

}