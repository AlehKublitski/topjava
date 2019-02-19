package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);
    private AtomicInteger userCount = new AtomicInteger(0);
    private Map<Integer, User> userRepository = new ConcurrentHashMap<>();

    @Override
    public boolean delete(int id) {
        boolean res = userRepository.remove(id) != null;
        log.info("delete {} {}", id, res);
        return res;
    }

    @Override
    public User save(User user) {
        if (user.isNew()) {
            user.setId(userCount.incrementAndGet());
            userRepository.put(user.getId(), user);
            log.info("save {}", user);
            return user;
        }
        return userRepository.computeIfPresent(user.getId(), (id, oldUser) -> user);
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        return userRepository.get(id);
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");
        return (List) userRepository.values();
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        User resUser = userRepository.values().stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(email))
                .findFirst().orElse(null);
        return resUser;
    }

}

