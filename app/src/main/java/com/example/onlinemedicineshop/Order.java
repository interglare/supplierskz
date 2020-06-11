package com.example.onlinemedicineshop;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Order implements Serializable{

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

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getDeliverySystem() {
        return deliverySystem;
    }

    public void setDeliverySystem(String deliverySystem) {
        this.deliverySystem = deliverySystem;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    @SerializedName("id")
    private int id;
    @SerializedName("userId")
    private int userId;
    @SerializedName("qtn")
    private int qtn;
    @SerializedName("cost")
    private int cost;
    @SerializedName("orderDate")
    private String orderDate;
    @SerializedName("deliveryDate")
    private String deliveryDate;
    @SerializedName("deliverySystem")
    private String deliverySystem;
    @SerializedName("payment")
    private String payment;
    @SerializedName("status")
    private String status;

    public int getSeen() {
        return seen;
    }

    public void setSeen(int seen) {
        this.seen = seen;
    }

    @SerializedName("seen")
    private int seen;
}
