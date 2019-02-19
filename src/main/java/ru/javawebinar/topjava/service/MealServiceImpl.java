package ru.javawebinar.topjava.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MealServiceImpl implements MealService {
    protected final Logger log = LoggerFactory.getLogger(getClass());
    private final MealRepository repository;

    @Autowired
    public MealServiceImpl(MealRepository repository) {
        this.repository = repository;
    }

    @Override
    public Meal create(Meal meal, int userId) {
        meal.setUserId(userId);
        return repository.save(meal);
    }

    @Override
    public void delete(int id, int userId) throws NotFoundException {
        if (repository.get(id) == null || repository.get(id).getUserId() != userId)
            throw new NotFoundException(String.valueOf(id));
        repository.delete(id);
    }

    @Override
    public Meal get(int id, int userId) throws NotFoundException {
        Meal meal = repository.get(id);
        if (meal == null | meal.getUserId() != userId) throw new NotFoundException(String.valueOf(id));
        return meal;
    }

    @Override
    public void update(Meal meal, int userId) throws NotFoundException {
        Meal meal1 = repository.get(meal.getId());
        if (meal == null || meal.getUserId() != userId) throw new NotFoundException(String.valueOf(meal.getId()));
        repository.save(meal);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return repository.getAll().stream()
                .filter(meal -> meal.getUserId() == userId)
                .sorted((m1, m2) -> (m2.getDate().compareTo(m1.getDate())))
                .collect(Collectors.toList());
    }
}