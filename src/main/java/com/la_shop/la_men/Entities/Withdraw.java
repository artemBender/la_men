package com.la_shop.la_men.Entities;

import java.time.LocalDate;
import jakarta.persistence.*;
@Entity
public class Withdraw {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double amount;
    private LocalDate created_at;
    @PrePersist
    public void prePersist() {
        created_at = LocalDate.now();
    }
    private String purpose;

    public Withdraw(double amount, String purpose) {
        this.amount = amount;
        this.purpose = purpose;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDate created_at) {
        this.created_at = created_at;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public Withdraw() {
    }
}

