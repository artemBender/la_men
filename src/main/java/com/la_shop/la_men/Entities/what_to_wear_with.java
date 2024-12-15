package com.la_shop.la_men.Entities;

import jakarta.persistence.*;

@Entity
public class what_to_wear_with {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long product_id, product_id_to_wear_with;

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

    public Long getProduct_id_to_wear_with() {
        return product_id_to_wear_with;
    }

    public void setProduct_to_wear_with(Long product_to_wear_with) {
        this.product_id_to_wear_with = product_to_wear_with;
    }
}
