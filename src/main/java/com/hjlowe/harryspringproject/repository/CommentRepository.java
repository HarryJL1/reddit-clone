package com.hjlowe.harryspringproject.repository;

import com.hjlowe.harryspringproject.model.Comment;
import com.hjlowe.harryspringproject.model.Post;
import com.hjlowe.harryspringproject.model.User;
import com.hjlowe.harryspringproject.service.CommentService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);

    List<Comment> findAllByUser(User user);

}
