package com.gmail.vx.rnd_news_generator.model.roles;

public enum UserRole {
    ADMIN, MODERATOR, USER;

    @Override
    public String toString() {
        return "ROLE_" + name();
    }
}
