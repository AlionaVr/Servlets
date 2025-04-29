package org.dto;

public class PostDTO {
    private long id;
    private String content;

    public PostDTO(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public PostDTO() {
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

}
