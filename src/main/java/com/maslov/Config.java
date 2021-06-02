package com.maslov;

import com.maslov.controller.PostController;
import com.maslov.repository.PostRepository;
import com.maslov.service.PostService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public PostRepository postRepository() {
        return new PostRepository();
    }

    @Bean
    public PostService postService() {
        return new PostService(postRepository());
    }

    @Bean
    public PostController postController() {
        return new PostController(postService());
    }

}