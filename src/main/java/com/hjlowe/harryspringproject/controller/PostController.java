package com.hjlowe.harryspringproject.controller;

import com.hjlowe.harryspringproject.dto.PostRequest;
import com.hjlowe.harryspringproject.dto.PostResponse;
import com.hjlowe.harryspringproject.service.PostService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/auth/posts/")
@AllArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity createPost(@RequestBody PostRequest postRequest) {
        postService.save(postRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id){
        return status(HttpStatus.OK).body(postService.getPost(id));
    }
    @GetMapping("/")
    public ResponseEntity<List<PostResponse>> getAllPosts(){
        return status(HttpStatus.OK).body(postService.getAllPosts());
    }
    @GetMapping("/by-subreddit/{id}")
    public ResponseEntity<List<PostResponse>> getAllPosts(@PathVariable Long id){
        return status(HttpStatus.OK).body(postService.getPostsBySubreddit(id));
    }
    @GetMapping("/by-user/{name}")
    public ResponseEntity<List<PostResponse>> getAllPosts(@PathVariable String name){
        return status(HttpStatus.OK).body(postService.getPostsByUsername(name));
    }
}
