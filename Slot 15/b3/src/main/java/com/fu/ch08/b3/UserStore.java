package com.fu.ch08.b3;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

/** In-memory account store for this exercise. */
public final class UserStore {
    private static final Map<String, User> USERS = new HashMap<>();

    static {
        add(new User("Quản trị viên", "admin@example.com", "admin", "Admin@123",
                "Khác", LocalDate.of(2000, 1, 1), "Kỹ thuật phần mềm", List.of()));
    }

    private UserStore() {
    }

    public static boolean exists(String username) {
        return USERS.containsKey(normalize(username));
    }

    public static void add(User user) {
        USERS.put(normalize(user.username()), user);
    }

    public static Optional<User> login(String username, String password) {
        return Optional.ofNullable(USERS.get(normalize(username)))
                .filter(user -> user.password().equals(password));
    }

    private static String normalize(String username) {
        return username.trim().toLowerCase(Locale.ROOT);
    }
}
