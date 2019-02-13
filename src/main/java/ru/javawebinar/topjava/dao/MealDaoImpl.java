package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class MealDaoImpl implements MealDao {
    private static List<Meal> list = new CopyOnWriteArrayList<Meal>();
    private static Map<Integer, Meal> map = new ConcurrentHashMap<>();
    public static AtomicInteger count = new AtomicInteger(0);

    static {
        map.put(count.incrementAndGet(), new Meal(count.get(), LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        map.put(count.incrementAndGet(), new Meal(count.get(), LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        map.put(count.incrementAndGet(), new Meal(count.get(), LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        map.put(count.incrementAndGet(), new Meal(count.get(), LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        map.put(count.incrementAndGet(), new Meal(count.get(), LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        map.put(count.incrementAndGet(), new Meal(count.get(), LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
        map.put(count.incrementAndGet(), new Meal(count.get(), LocalDateTime.of(2015, Month.MAY, 29, 10, 0), "Завтрак", 500));
        map.put(count.incrementAndGet(), new Meal(count.get(), LocalDateTime.of(2015, Month.MAY, 29, 13, 0), "Обед", 900));
        map.put(count.incrementAndGet(), new Meal(count.get(), LocalDateTime.of(2015, Month.MAY, 29, 19, 0), "Ужин", 400));
    }

    @Override
    public void addMeal(Meal meal) {
        meal.setMealId(count.incrementAndGet());
        map.put(meal.getMealId(), meal);
    }

    @Override
    public void removeMeal(int id) {
        map.remove(id);
    }

    @Override
    public void updateMeal(Meal meal) {
        map.put(meal.getMealId(), meal);
    }

    @Override
    public Meal getMealById(int id) {
        return map.get(id);
    }

    @Override
    public List<Meal> listMeal() {
        return map.values().stream().collect(Collectors.toList());
    }
}
