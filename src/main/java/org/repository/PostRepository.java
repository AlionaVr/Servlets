package org.repository;

import org.exception.NotFoundException;
import org.model.Post;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class PostRepository {
    private final Map<Long, Post> posts = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(0);


    public List<Post> all() {
        if (posts.isEmpty()) {
            return Collections.emptyList();
        }
        return posts.values().stream()
                .map(Post::new)
                .collect(Collectors.toList());
    }

    public Optional<Post> getById(long id) {
        checkId(id);
        if (posts.isEmpty()) {
            throw new NotFoundException();
        } else
            return posts.values().stream()
                    .filter(post -> post.getId() == id)
                    .findFirst();
    }

    public Post save(Post post) {
        if (post == null) {
            throw new IllegalArgumentException("Post cannot be null");
        }
        if (post.getId() == 0) {
            long newId = idCounter.incrementAndGet();
            Post newPost = new Post(post);
            newPost.setId(newId);
            posts.put(newId, newPost);
            return newPost;
        } else {
            if (!posts.containsKey(post.getId())) {
                throw new NotFoundException();
            }
        }
        posts.put(post.getId(), new Post(post));
        return post;
    }

    public void removeById(long id) {
        checkId(id);
        if (posts.remove(id) == null) {
            throw new NotFoundException();
        }
    }

    private void checkId(Long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be positive");
        }
    }
}
