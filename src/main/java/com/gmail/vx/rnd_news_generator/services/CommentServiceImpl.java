package com.gmail.vx.rnd_news_generator.services;


import com.gmail.vx.rnd_news_generator.model.Comment;
import com.gmail.vx.rnd_news_generator.model.Source;
import com.gmail.vx.rnd_news_generator.model.Subject;
import com.gmail.vx.rnd_news_generator.repos.CommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService{
    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Transactional
    @Override
    public void addComment(Comment comment) {
        commentRepository.save(comment);
    }

    @Transactional(readOnly=true)
    @Override
    public List<Comment> findBySubject(long id) {
        return commentRepository.findBySubjectId(id);
    }

    @Transactional(readOnly=true)
    @Override
    public List<Comment> findBySubjectAllowed(long id) {
        List<Comment> allowedComments = new ArrayList<>();
        List<Comment> comments = commentRepository.findBySubjectId(id);

        for (Comment comment : comments) {
            if (!comment.isPseudoDeleted()) {
                allowedComments.add(comment);
            }
        }

        return allowedComments;
    }

    @Transactional(readOnly=true)
    @Override
    public List<Comment> findPseudoDeletedComments() {
       // List<Comment> pseudoDeletedComments = new ArrayList<>();
        return commentRepository.findPseudoDeleted();

       /* for (Comment comment : comments) {
            if (comment.isPseudoDeleted()) {
                pseudoDeletedComments.add(comment);
            }
        }
        return pseudoDeletedComments;*/
    }

    @Transactional
    @Override
    public void pseudoDelete(long id) {
            Comment comment = commentRepository.findCommentById(id);
            comment.setPseudoDeleted(true);
            commentRepository.save(comment);

    }

    @Transactional
    @Override
    public void deleteComments(long[] idList) {
        for (long id : idList)
            commentRepository.deleteById(id);
    }

    @Transactional
    @Override
    public List<Comment> findByUser(String login) {
        return commentRepository.findByUserLogin(login);
    }

    @Transactional(readOnly=true)
    @Override
    public Comment findById(long id) {
        return commentRepository.findCommentById(id);
    }

    @Transactional(readOnly=true)
    @Override
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }


    @Transactional(readOnly=true)
    @Override
    public List<Comment> findByDate(int month, int day) {
        List<Comment> sortedComments = new ArrayList<>();

        List<Comment> comments = findAll();
        for (int i = 0; i < comments.size(); i++) {
            String commentMonth = comments.get(i).getDate().split(" ")[1];
            String commentDay = comments.get(i).getDate().split(" ")[2];
            if (Integer.parseInt(commentMonth) == month && Integer.parseInt(commentDay) == day) {
                sortedComments.add(comments.get(i));
            }
        }
        return sortedComments;

    }
}
