package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.*;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(14,0), 2000);

    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        List<UserMealWithExceed> exceedList = new ArrayList<>();
        Map<LocalDate, List<UserMeal>> map = mealList.stream().sorted(Comparator.comparing(UserMeal::getDateTime)).collect(Collectors.groupingBy((p) -> p.getDateTime().toLocalDate()));
        for(Map.Entry entry: map.entrySet()) {
            List<UserMeal> mealListForEveryDate = (List<UserMeal>)entry.getValue();
            int caloriesThisDay = mealListForEveryDate.stream().mapToInt((p) -> p.getCalories()).sum();
            exceedList.addAll(mealListForEveryDate.stream().filter(p -> p.getDateTime().toLocalTime().isAfter(startTime) && p.getDateTime().toLocalTime().isBefore(endTime)).
                    map((p) -> new UserMealWithExceed(p.getDateTime(), p.getDescription(), p.getCalories(), (caloriesThisDay <= caloriesPerDay))).collect(Collectors.toList()));
        }

        return exceedList;
    }
}
