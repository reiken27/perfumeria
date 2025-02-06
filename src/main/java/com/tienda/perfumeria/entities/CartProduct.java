package com.tienda.perfumeria.entities;

public class CartProduct {

    private Integer productId;
    private String productName;
    private Integer quantity;
    private double price;

    public CartProduct() {
    }

    public CartProduct(Integer productId, String productName, Integer quantity, double price) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    public Integer getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
