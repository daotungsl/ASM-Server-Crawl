package com.asm.java.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Article {

    @Id
    private String link;
    @Index
    private String title;
    @Index
    private String img;
    @Index
    private String description;
    @Index
    private String content;
    @Index
    private String author;
    @Index
    private String source;
    @Index
    private String url_category;
    @Index
    private String url_detail;
    @Index
    private long createdAtMLS;
    @Index
    private long updatedAtMLS;
    @Index
    private long deletedAtMLS;
    @Index
    private long publishedAtMLS;
    @Index
    private int status; // 0.pending | 1. indexed. | -1. deleted.

    public Article() {
    }

    public Article(String link, Status status) {
        this.link = link;
        this.status = status.getValue();

    }

    public Article(String link, String img, Status status) {
        this.link = link;
        this.img = img;
        this.status = status.getValue();
    }

    public Article(String link, String title,String img, String description, String content, String source, String url_category, String url_detail) {
        this.link = link;
        this.title = title;
        this.img = img;
        this.description = description;
        this.content = content;
        this.source = source;
        this.url_category = url_category;
        this.url_detail = url_detail;

    }

    public enum Status {
        PENDING(0), INDEXED(1), DELETED(-1);

        int value;

        Status(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public long getCreatedAtMLS() {
        return createdAtMLS;
    }

    public void setCreatedAtMLS(long createdAtMLS) {
        this.createdAtMLS = createdAtMLS;
    }

    public long getUpdatedAtMLS() {
        return updatedAtMLS;
    }

    public void setUpdatedAtMLS(long updatedAtMLS) {
        this.updatedAtMLS = updatedAtMLS;
    }

    public long getDeletedAtMLS() {
        return deletedAtMLS;
    }

    public void setDeletedAtMLS(long deletedAtMLS) {
        this.deletedAtMLS = deletedAtMLS;
    }

    public long getPublishedAtMLS() {
        return publishedAtMLS;
    }

    public void setPublishedAtMLS(long publishedAtMLS) {
        this.publishedAtMLS = publishedAtMLS;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl_category() {
        return url_category;
    }

    public void setUrl_category(String url_category) {
        this.url_category = url_category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUrl_detail() {
        return url_detail;
    }

    public void setUrl_detail(String url_detail) {
        this.url_detail = url_detail;
    }
}