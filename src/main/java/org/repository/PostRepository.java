package org.repository;

import org.model.Post;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PostRepository {
    private final Map<Long, Post> posts = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(0);


    public List<Post> findAll() {
        if (posts.isEmpty()) {
            return Collections.emptyList();
        }
        return posts.values().stream()
                .map(Post::new)
                .toList();
    }

    public Optional<Post> findById(long id) {
        return Optional.ofNullable(posts.get(id)).map(Post::new);
    }

    public Post save(Post post) {
        if (post.getId() == 0) {
            long newId = idCounter.incrementAndGet();
            Post newPost = new Post(post);
            newPost.setId(newId);
            posts.put(newId, newPost);
            return newPost;
        }
        posts.put(post.getId(), new Post(post));
        return post;
    }
}
