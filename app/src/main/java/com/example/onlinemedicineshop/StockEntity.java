package com.example.onlinemedicineshop;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StockEntity implements Serializable{

    private static final long serialVersionUID=1L;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getPrice1() {
        return price1;
    }

    public void setPrice1(int price1) {
        this.price1 = price1;
    }

    public int getPrice2() {
        return price2;
    }

    public void setPrice2(int price2) {
        this.price2 = price2;
    }

    public int getSells() {
        return sells;
    }

    public void setSells(int sells) {
        this.sells = sells;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }


    @SerializedName("id")
    private int id;
    @SerializedName("brand")
    private String brand;
    @SerializedName("type")
    private String type;
    @SerializedName("name")
    private String name;
    @SerializedName("image")
    private String image;
    @SerializedName("unit")
    private String unit;
    @SerializedName("qtn")
    private int qtn;
    @SerializedName("price1")
    private int price1;
    @SerializedName("price2")
    private int price2;
    @SerializedName("sells")
    private int sells;
    @SerializedName("info")
    private String info;

}
