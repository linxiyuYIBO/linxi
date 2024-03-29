package com.linxi.goods;

public class Goods {

    private String id;// 商品编号
    private String name;// 商品名称
    private double price;// 商品价格

    public Goods(String id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public double getPrice() {
        return this.price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("[%2s] %s %.2f", this.getId(), this.getName(), this.getPrice());
    }
}
