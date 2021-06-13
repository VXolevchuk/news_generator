package com.gmail.vx.rnd_news_generator.services;

import com.gmail.vx.rnd_news_generator.model.Comment;
import com.gmail.vx.rnd_news_generator.model.CustomUser;
import com.gmail.vx.rnd_news_generator.model.Subject;
import com.gmail.vx.rnd_news_generator.model.roles.UserRole;

import java.util.List;

public interface UserService {
    List<CustomUser> getAllUsers();
    CustomUser findByLogin(String login);
    boolean addUser(String login, String passHash,
                    UserRole role);
    void deleteUsers(List<Long> ids);
    boolean addLikedSubject(String login, long subjectId);
    void blockUser(String login);
    void unblockUser(String login);
    List<Comment> viewUserComments(String login);
    int countReports(String login);

    void setModerator(String login);




}
