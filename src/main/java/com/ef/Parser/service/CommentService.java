package com.ef.Parser.service;

import com.ef.Parser.model.Comment;
import com.ef.Parser.model.Log;
import com.ef.Parser.repository.CommentRepository;
import com.ef.Parser.util.Arguments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public List<Comment> saveComments(Arguments params, List<Log> logs) {
        List<Comment> comments = new ArrayList<>();

        for (Log log : logs) {
            Comment comment = new Comment();
            comment.setIp(log.getIp());
            comment.setComment(log.getIp() + " has reached more than " + params.getThreshold().toString() + " requests between " + params.getStartDate().toString() + " and " + params.getEndDate().toString());
            comments.add(comment);
        }
        commentRepository.save(comments);

        return comments;
    }
}
