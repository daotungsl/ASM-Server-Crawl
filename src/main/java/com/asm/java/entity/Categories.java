package com.asm.java.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Categories {
    @Id
    private String url_category;
    @Index
    private String category;
    @Index
    private long createdAtMLS;
    @Index
    private long updatedAtMLS;
    @Index
    private long deletedAtMLS;

    public Categories() {
    }

    public Categories(String url_category, String category) {
        this.url_category = url_category;
        this.category = category;
    }

    public Categories(String url_category, String category, long createdAtMLS, long updatedAtMLS, long deletedAtMLS) {
        this.url_category = url_category;
        this.category = category;
        this.createdAtMLS = createdAtMLS;
        this.updatedAtMLS = updatedAtMLS;
        this.deletedAtMLS = deletedAtMLS;
    }

    public String getUrl_category() {
        return url_category;
    }

    public void setUrl_category(String url_category) {
        this.url_category = url_category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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
}
