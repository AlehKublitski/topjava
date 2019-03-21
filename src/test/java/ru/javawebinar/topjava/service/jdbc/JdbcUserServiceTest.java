package ru.javawebinar.topjava.service.jdbc;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

import static ru.javawebinar.topjava.Profiles.JDBC;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(JDBC)
public class JdbcUserServiceTest extends AbstractUserServiceTest {


    @Test
    public void getUserWithRoles() {
        User user = service.getWithRoles(ADMIN_ID);
        assertMatchRoles(user.getRoles(), ADMIN.getRoles());
    }
}