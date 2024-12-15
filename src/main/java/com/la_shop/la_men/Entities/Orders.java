package com.la_shop.la_men.Entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long user_id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Products products;
    private int quantity, checkNumber;
    private String size, status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String  destination;
    private LocalDate delivery_date, created_at;


   @PrePersist
   public void prePersist() {
            created_at = LocalDate.now();
        }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public Orders() {
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Orders(Long user_id, Products products, int quantity, int checkNumber, String size, LocalDate delivery_date, String destination, String status) {
        this.user_id = user_id;
        this.products = products;
        this.quantity = quantity;
        this.checkNumber = checkNumber;
        this.size = size;
        this.destination = destination;
        this.delivery_date = delivery_date;
        this.status = status;

    }

    public int getCheckNumber() {
        return checkNumber;
    }

    public void setCheckNumber(int checkNumber) {
        this.checkNumber = checkNumber;
    }

    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String  size) {
        this.size = size;
    }

    public LocalDate getDelivery_date() {
        return delivery_date;
    }

    public void setDelivery_date(LocalDate delivery_date) {
        this.delivery_date = delivery_date;
    }

    public LocalDate getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDate created_at) {
        this.created_at = created_at;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
