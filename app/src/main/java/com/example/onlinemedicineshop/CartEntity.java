package com.example.onlinemedicineshop;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CartEntity implements Serializable{

    private static final long serialVersionUID=1L;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getQtn() {
        return qtn;
    }

    public void setQtn(int qtn) {
        this.qtn = qtn;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @SerializedName("id")
    private int id;
    @SerializedName("userId")
    private int userId;
    @SerializedName("stockId")
    private int stockId ;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @SerializedName("name")
    private String name;
    @SerializedName("image")
    private String image;
    @SerializedName("qtn")
    private int qtn;
    @SerializedName("cost")
    private int cost;
}
