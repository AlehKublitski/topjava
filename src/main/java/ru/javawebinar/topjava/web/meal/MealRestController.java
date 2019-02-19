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

    public List<MealTo> getAll() {
        log.info("getAll");
        return MealsUtil.getWithExcess(service.getAll(authUserId()), MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    public List<MealTo> getFiltred(String startTime, String endTime, String startDate, String endDate) {
        LocalTime startTimeL = startTime.isEmpty() ? LocalTime.MIN : LocalTime.parse(startTime);
        LocalTime endTimeL = endTime.isEmpty() ? LocalTime.MAX : LocalTime.parse(endTime);
        LocalDate startDateL = startDate.isEmpty() ? LocalDate.MIN : LocalDate.parse(startDate);
        LocalDate endDateL = endDate.isEmpty() ? LocalDate.MAX : LocalDate.parse(endDate);
        return MealsUtil.getFilteredWithExcess(service.getAll(authUserId()), MealsUtil.DEFAULT_CALORIES_PER_DAY, startTimeL, endTimeL, startDateL, endDateL);
    }

    public Meal get(int id) {
        log.info("get {}", id);
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