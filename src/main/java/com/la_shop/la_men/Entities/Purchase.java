package com.la_shop.la_men.Entities;

import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Products products;
    private int size, quantity;
    private double price, cost;

    private LocalDate created_at;
    @PrePersist
    public void prePersist() {
        created_at = LocalDate.now();
    }

    public void setCreated_at(LocalDate created_at) {
        this.created_at = created_at;
    }
    public LocalDate getCreated_at() {
        return created_at;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }

    public int getSize() {
        return size;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Purchase(Products products, int size, int quantity, double price, double cost) {
        this.products = products;
        this.size = size;
        this.quantity = quantity;
        this.price = price;
        this.cost = cost;
    }

    public Purchase() {
    }
}