package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int MEAL1_ID = START_SEQ + 2;
    public static final int MEAL2_ID = START_SEQ + 3;
    public static final int MEAL3_ID = START_SEQ + 4;

    public static final Meal MEAL1 = new Meal(MEAL1_ID, LocalDateTime.parse("2019-12-12T10:10"), "ланч", 500);
    public static final Meal MEAL2 = new Meal(MEAL2_ID, LocalDateTime.parse("2019-12-12T14:10"), "обед", 1000);
    public static final Meal MEAL3 = new Meal(MEAL3_ID, LocalDateTime.parse("2019-12-12T02:10"), "ужин", 500);
    public static final Meal UPDATEMEAL = new Meal(MEAL3_ID, LocalDateTime.parse("2015-12-12T12:12"), "ужин НОВЫЙ", 1500);

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }
}
