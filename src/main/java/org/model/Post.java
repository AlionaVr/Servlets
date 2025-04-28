package org.model;

public class Post {
    private long id;
    private String content;
    private boolean removed;

    public Post(Post post) {
        this.id = post.id;
        this.content = post.content;
    }

    public Post(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public Post() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isRemoved() {
        return removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }
}
