package com.asm.java.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class PreviewDTO {
    @Id
    private String previewLink ;

    private String titleSelector;

    private String desSelector;

    private String contentSelector;

    public PreviewDTO() {
    }

    public PreviewDTO(String previewLink, String titleSelector, String desSelector, String contentSelector) {
        this.previewLink = previewLink;
        this.titleSelector = titleSelector;
        this.desSelector = desSelector;
        this.contentSelector = contentSelector;
    }

    public String getPreviewLink() {
        return previewLink;
    }

    public void setPreviewLink(String previewLink) {
        this.previewLink = previewLink;
    }

    public String getTitleSelector() {
        return titleSelector;
    }

    public void setTitleSelector(String titleSelector) {
        this.titleSelector = titleSelector;
    }

    public String getContentSelector() {
        return contentSelector;
    }

    public void setContentSelector(String contentSelector) {
        this.contentSelector = contentSelector;
    }

    public String getDesSelector() {
        return desSelector;
    }

    public void setDesSelector(String desSelector) {
        this.desSelector = desSelector;
    }
}
