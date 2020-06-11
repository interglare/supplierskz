package com.example.onlinemedicineshop;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProductTipsEntity implements Serializable {

    private static final long serialVersionUID=1L;
    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @SerializedName("image")

    private String image;
    @SerializedName("description")
    private String description ;
}
