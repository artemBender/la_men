package com.la_shop.la_men.Entities;

import jakarta.persistence.*;

@Entity
public class Storehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long product_id;
    private int size, quantity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public int getSize() {
        return size;
    }

    public Storehouse() {
    }

    public Storehouse(Long product_id, int size, int quantity) {
        this.product_id = product_id;
        this.size = size;
        this.quantity = quantity;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
