package com.hjlowe.harryspringproject.service;

import com.hjlowe.harryspringproject.dto.PostRequest;
import com.hjlowe.harryspringproject.dto.PostResponse;
import com.hjlowe.harryspringproject.dto.SubredditDto;
import com.hjlowe.harryspringproject.exceptions.SpringRedditException;
import com.hjlowe.harryspringproject.mapper.PostMapper;
import com.hjlowe.harryspringproject.model.Post;
import com.hjlowe.harryspringproject.model.Subreddit;
import com.hjlowe.harryspringproject.model.User;
import com.hjlowe.harryspringproject.repository.PostRepository;
import com.hjlowe.harryspringproject.repository.SubredditRepository;
import com.hjlowe.harryspringproject.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final SubredditRepository subredditRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final PostMapper postMapper;

    public void save(PostRequest postRequest) {
        Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName())
                .orElseThrow(() -> new SpringRedditException(postRequest.getSubredditName()));
        postRepository.save(postMapper.map(postRequest, subreddit, authService.getCurrentUser()));
    }


    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new SpringRedditException(""));
        return postMapper.mapToDto(post);
    }

    public List<PostResponse> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }

    public List<PostResponse> getPostsBySubreddit(Long id) {
        Subreddit subreddit = subredditRepository.findById(id)
                .orElseThrow(() -> new SpringRedditException(""));
        return postRepository.findAllBySubreddit(subreddit)
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }

    public List<PostResponse> getPostsByUsername(String name) {
        User username = userRepository.findByUsername(name)
                .orElseThrow(() -> new SpringRedditException(""));
        return postRepository.findByUser(username)
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }
}
