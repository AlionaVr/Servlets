package org.controller;

import org.dto.PostDTO;
import org.service.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    @GetMapping
    public List<PostDTO> all() {
        return service.all();
    }

    @GetMapping("/{id}")
    public PostDTO getById(@PathVariable("id") long id) {
        return service.getById(id);
    }

    @PostMapping
    public PostDTO save(@RequestBody PostDTO post) {
        return service.save(post);
    }

    @DeleteMapping("/{id}")
    public void removeById(@PathVariable("id") long id) {
        service.removeById(id);
    }
}
