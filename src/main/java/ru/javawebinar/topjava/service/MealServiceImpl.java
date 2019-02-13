package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoImpl;
import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public class MealServiceImpl implements MealService {
    private MealDao mealDao = new MealDaoImpl();

    @Override
    public void addMeal(Meal meal) {
        mealDao.addMeal(meal);
    }

    @Override
    public void removeMeal(int id) {
        mealDao.removeMeal(id);
    }

    @Override
    public void updateMeal(Meal meal) {
        mealDao.updateMeal(meal);
    }

    @Override
    public Meal getMealById(int id) {
        return mealDao.getMealById(id);
    }

    @Override
    public List<Meal> listMeal() {
        return mealDao.listMeal();
    }
}
