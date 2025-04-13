package com.taskmanager.util;

import com.taskmanager.model.User;
import java.util.Map;

public class UserAuth {
    private static final String DEFAULT_AVATAR = "/images/pfp/blank_pfp.png";
    
    // === DEVELOPER NOTE: ADD NEW USERS HERE === //
    public static final Map<String, User> USER_DATABASE = Map.of(
        "oop", new User("123", DEFAULT_AVATAR),
        // Add more users below this line
        "alex", new User("pass456", DEFAULT_AVATAR),
        "sam", new User("789", DEFAULT_AVATAR)
    );

    public static boolean authenticate(String username, String password) {
        User user = USER_DATABASE.get(username);
        return user != null && user.getPassword().equals(password);
    }

    public static String getUserAvatar(String username) {
        User user = USER_DATABASE.get(username);
        return user != null ? user.getAvatarPath() : DEFAULT_AVATAR;
    }
} 