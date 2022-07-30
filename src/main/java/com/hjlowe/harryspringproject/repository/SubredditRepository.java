package com.hjlowe.harryspringproject.repository;

import com.hjlowe.harryspringproject.model.Post;
import com.hjlowe.harryspringproject.model.Subreddit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubredditRepository extends JpaRepository<Subreddit, Long> {

    Optional<Subreddit> findByName(String subredditName);
}
