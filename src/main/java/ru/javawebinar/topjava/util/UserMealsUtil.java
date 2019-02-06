package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );

        getFilteredWithExceeded(mealList, LocalTime.of(10, 0), LocalTime.of(13, 0), 2000);
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> maps = new HashMap<>();
        mealList.forEach(v -> maps.merge(v.getDateTime().toLocalDate(), v.getCalories(), Integer::sum));
        List<UserMealWithExceed> exceedList = new ArrayList<>();
        mealList.forEach(v -> {
            if (TimeUtil.isBetween(v.getDateTime().toLocalTime(), startTime, endTime))
                exceedList.add(new UserMealWithExceed(v.getDateTime(), v.getDescription(), v.getCalories(), maps.get(v.getDateTime().toLocalDate()) > caloriesPerDay));
        });

        return exceedList;
    }

    public static List<UserMealWithExceed> getFilteredWithExceededByStreamAPI(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> map = mealList.stream()
                .collect(Collectors.groupingBy(p -> p.getDateTime().toLocalDate(), Collectors.summingInt(UserMeal::getCalories)));

        return mealList.stream()
                .filter(p -> TimeUtil.isBetween(p.getDateTime().toLocalTime(), startTime, endTime))
                .map(p -> new UserMealWithExceed(p.getDateTime(), p.getDescription(), p.getCalories(), (map.get(p.getDateTime().toLocalDate()) > caloriesPerDay)))
                .collect(Collectors.toList());
    }
}
