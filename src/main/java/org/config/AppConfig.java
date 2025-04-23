package org.config;

import org.controller.PostController;
import org.repository.PostRepository;
import org.service.PostService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    PostRepository postRepository() {
        return new PostRepository();
    }

    @Bean
    PostService postService(PostRepository postRepository) {
        return new PostService(postRepository);
    }

    @Bean
    PostController postController(PostService postService) {
        return new PostController(postService);
    }
}
