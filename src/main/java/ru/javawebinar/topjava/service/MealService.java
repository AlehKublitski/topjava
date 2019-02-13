package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealService {
    public void addMeal(Meal meal);

    public void removeMeal(int id);

    public void updateMeal(Meal meal);

    public Meal getMealById(int id);

    public List<Meal> listMeal();
}
