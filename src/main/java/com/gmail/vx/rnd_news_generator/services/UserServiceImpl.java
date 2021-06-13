package com.gmail.vx.rnd_news_generator.services;

import com.gmail.vx.rnd_news_generator.config.AppConfig;
import com.gmail.vx.rnd_news_generator.model.Comment;
import com.gmail.vx.rnd_news_generator.model.CustomUser;
import com.gmail.vx.rnd_news_generator.model.Subject;
import com.gmail.vx.rnd_news_generator.model.roles.UserRole;
import com.gmail.vx.rnd_news_generator.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    @Autowired
    private SubjectService subjectService;
    @Autowired
    private CommentService commentService;


    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<CustomUser> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public CustomUser findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Transactional
    @Override
    public boolean addUser(String login, String passHash,
                           UserRole role) {
        if (userRepository.existsByLogin(login))
            return false;

        CustomUser user = new CustomUser(login, passHash, role, false);
        userRepository.save(user);

        return true;
    }

    @Transactional
    @Override
    public void deleteUsers(List<Long> ids) {
        ids.forEach(id -> {
            Optional<CustomUser> user = userRepository.findById(id);
            user.ifPresent(u -> {
                if ( ! AppConfig.ADMIN.equals(u.getLogin())) {
                    userRepository.deleteById(u.getId());
                }
            });
        });
    }

    @Transactional
    @Override
    public boolean addLikedSubject(String login, long subjectId) {
        CustomUser customUser = userRepository.findByLogin(login);
        Subject subject = subjectService.findById(subjectId);
         if(!subjectService.findByUser(customUser).contains(subject)) {
             customUser.addLikedSubject(subject);
             userRepository.save(customUser);
             return true;
         }

         return false;
    }

    @Transactional
    @Override
    public void blockUser(String login) {
        CustomUser customUser = userRepository.findByLogin(login);
        customUser.setBlocked(true);
        userRepository.save(customUser);
    }

    @Transactional
    @Override
    public void unblockUser(String login) {
        CustomUser customUser = userRepository.findByLogin(login);
        customUser.setBlocked(false);
        userRepository.save(customUser);
    }

    @Transactional
    @Override
    public List<Comment> viewUserComments(String login) {
        CustomUser customUser = userRepository.findByLogin(login);

        return commentService.findByUser(customUser.getLogin());
    }

    @Transactional(readOnly = true)
    @Override
    public int countReports(String login) {
        CustomUser customUser = userRepository.findByLogin(login);
        return customUser.countReports();
    }

    @Transactional
    @Override
    public void setModerator(String login) {
        CustomUser customUser = userRepository.findByLogin(login);
        if (customUser.getRole() != UserRole.ADMIN && customUser.getRole() != UserRole.MODERATOR && !customUser.isBlocked()) {
            customUser.setRole(UserRole.MODERATOR);
        }
        userRepository.save(customUser);
    }

}
