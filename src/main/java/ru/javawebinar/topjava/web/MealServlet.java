package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.MealServiceImpl;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MealServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);
    private static String INSERT_OR_EDIT = "/meal.jsp";
    private static String LIST_MEALS = "/meals.jsp";

    private MealService mealService;
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm");

    public MealServlet() {
        super();
        mealService = new MealServiceImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward = "";
        String action = request.getParameter("action");

        if (action == null) {
            LOG.debug("action = null");
            forward = LIST_MEALS;
            request.setAttribute("mealList", MealsUtil.getListMealToFromMeal());
        } else if (action.equalsIgnoreCase("delete")) {
            int mealId = Integer.parseInt(request.getParameter("mealId"));
            mealService.removeMeal(mealId);
            forward = LIST_MEALS;
            request.setAttribute("mealList", MealsUtil.getListMealToFromMeal());
            LOG.debug("Delete meal by id{}", mealId);
        } else if (action.equalsIgnoreCase("edit")) {
            int mealId = Integer.parseInt(request.getParameter("mealId"));
            Meal meal = mealService.getMealById(mealId);
            forward = INSERT_OR_EDIT;
            request.setAttribute("meal", meal);
            LOG.debug("Edit meal by id{}", mealId);
        } else if (action.equalsIgnoreCase("add")) {

            Meal meal = new Meal(0, null, null, 0);
            forward = INSERT_OR_EDIT;
            request.setAttribute("meal", meal);
            LOG.debug("Try add new meal");
        } else {
            forward = LIST_MEALS;
            request.setAttribute("mealList", MealsUtil.getListMealToFromMeal());
            LOG.debug("Redirect listMeals table");
        }
        request.setAttribute("formatter", formatter);
        request.getRequestDispatcher(forward).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        int mealId = Integer.parseInt(request.getParameter("mealId"));
        int calories = Integer.parseInt(request.getParameter("calories"));
        String description = request.getParameter("description");
        LocalDateTime localDateTime = LocalDateTime.parse(request.getParameter("date"));
        Meal meal = new Meal(mealId, localDateTime, description, calories);
        if (meal.getMealId() == 0) mealService.addMeal(meal);
        else mealService.updateMeal(meal);
        request.setAttribute("mealList", MealsUtil.getListMealToFromMeal());
        request.setAttribute("formatter", formatter);
        request.getRequestDispatcher(LIST_MEALS).forward(request, response);
    }
}
