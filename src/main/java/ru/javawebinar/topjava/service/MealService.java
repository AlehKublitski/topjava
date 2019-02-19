package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

public interface MealService {

    Meal create(Meal meal, int i);

    void delete(int i, int id) throws NotFoundException;

    Meal get(int i, int id) throws NotFoundException;

    void update(Meal meal, int i);

    List<Meal> getAll(int i);
}