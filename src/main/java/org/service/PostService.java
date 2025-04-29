package org.service;

import org.dto.PostDTO;
import org.exception.NotFoundException;
import org.model.Post;
import org.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private final PostRepository repository;

    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    public List<PostDTO> all() {
        return repository.findAll().stream()
                .filter(post -> !post.isRemoved())
                .map(post -> new PostDTO(post.getId(), post.getContent()))
                .toList();
    }

    public PostDTO getById(long id) {
        checkId(id);
        Post entity = repository.findById(id)
                .filter(post -> !post.isRemoved())
                .orElseThrow(NotFoundException::new);
        return new PostDTO(entity.getId(), entity.getContent());
    }

    public PostDTO save(PostDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Post cannot be null");
        }
        Post toSave = new Post(dto.getId(), dto.getContent());

        // CREATE
        if (dto.getId() == 0) {
            Post saved = repository.save(toSave);
            return new PostDTO(saved.getId(), saved.getContent());
        }
        // UPDATE
        Post stored = repository.findById(dto.getId())
                .filter(p -> !p.isRemoved())
                .orElseThrow(NotFoundException::new);

        stored.setContent(dto.getContent());
        Post updated = repository.save(stored);
        return new PostDTO(updated.getId(), updated.getContent());
    }

    public void removeById(long id) {
        checkId(id);
        Post stored = repository.findById(id)
                .filter(p -> !p.isRemoved())
                .orElseThrow(NotFoundException::new);
        stored.setRemoved(true);
        repository.save(stored);
    }

    private void checkId(Long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be positive");
        }
    }
}

