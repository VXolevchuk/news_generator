package com.gmail.vx.rnd_news_generator.services;

import com.gmail.vx.rnd_news_generator.model.Comment;
import com.gmail.vx.rnd_news_generator.model.CustomUser;
import com.gmail.vx.rnd_news_generator.model.Source;
import com.gmail.vx.rnd_news_generator.model.Subject;
import com.gmail.vx.rnd_news_generator.repos.SubjectRepository;
import com.gmail.vx.rnd_news_generator.services.sorter.ShellSorter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class SubjectServiceImpl implements SubjectService{
    private final SubjectRepository subjectRepository;

    @Autowired
    private CommentService commentService;

    @Autowired
    private ShellSorter sorter;

    public SubjectServiceImpl(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Transactional
    @Override
    public void addSubject(Subject subject) {
        subjectRepository.save(subject);
    }

    @Transactional
    @Override
    public void deleteSubjects(long[] idList) {
        for (long id : idList)
            subjectRepository.deleteById(id);
    }

    @Transactional(readOnly=true)
    @Override
    public List<Subject> findAll() {
        return subjectRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public long count() {
        return subjectRepository.count();
    }

    @Transactional(readOnly = true)
    @Override
    public long countBySource(Source source) {
        return subjectRepository.countBySource(source);
    }

    @Transactional(readOnly=true)
    @Override
    public List<Subject> findBySource(Source source) {
        return subjectRepository.findBySource(source);
    }

    @Transactional(readOnly=true)
    @Override
    public List<Subject> findByPattern(String pattern) {
        return subjectRepository.findByPattern(pattern);
    }

    @Transactional(readOnly=true)
    @Override
    public boolean existsByUrl(String url) {
        return subjectRepository.existsByUrl(url);
    }

    @Transactional(readOnly=true)
    @Override
    public Subject findById(long id) {
        return subjectRepository.findById(id);
    }

    @Transactional(readOnly=true)
    @Override
    public List<Subject> findByUser(CustomUser customUser) {
        return subjectRepository.findByUser(customUser.getId());
    }

    @Transactional(readOnly=true)
    @Override
    public List<Subject> findByDate(int month, int day) {
        List<Subject> sortedSubjects = new ArrayList<>();

        List<Subject> subjects = subjectRepository.findAll();
        for (int i = 0; i < subjects.size(); i++) {
            String subjectMonth = subjects.get(i).getDate().split(" ")[1];
            String subjectDay = subjects.get(i).getDate().split(" ")[2];
            if (Integer.parseInt(subjectMonth) == month && Integer.parseInt(subjectDay) == day) {
                sortedSubjects.add(subjects.get(i));
            }
        }
        return sortedSubjects;
    }

    @Transactional(readOnly=true)
    @Override
    public List<Subject> findByUserComments(CustomUser customUser) {
        List<Subject> subjects = new ArrayList<>();
        List<Comment> comments = commentService.findByUser(customUser.getLogin());
        for (Comment comment: comments) {
            long id = comment.getSubject().getId();
            subjects.add(subjectRepository.findById(id));
        }

        return subjects;
    }

    @Transactional(readOnly=true)
    @Override
    public Subject[] sortByLikes() {
        List<Subject> list = findAll();
        Subject [] subjects = list.toArray(Subject[]::new);
        sorter.sortByLikes(subjects);
        return subjects;

    }

    @Transactional(readOnly=true)
    @Override
    public Subject[] sortByComments() {
        List<Subject> list = findAll();
        Subject [] subjects = list.toArray(Subject[]::new);
        sorter.sortByComments(subjects);
        return subjects;
    }

    @Transactional(readOnly=true)
    @Override
    public  List<Subject> findCommentedSubjects() {
        return subjectRepository.findCommentedSubjects();
    }

    @Transactional(readOnly=true)
    @Override
    public List<Subject> getSubjectsForMainPage(List<Source> sources) {
        List<Subject> subjectsToReturn = new ArrayList<>();
        for (Source source : sources) {
            List<Subject> subjects = findBySource(source);
            ArrayDeque<Subject> subjects1 = new ArrayDeque<Subject>();
            for (int i = 0; i <= 3 && i < subjects.size(); i++) {
                subjects1.add(subjects.get(i));
            }

            while (subjects1.peek() != null) {
                subjectsToReturn.add(subjects1.poll());
            }
        }
        return subjectsToReturn;
    }






}
