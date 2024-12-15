package com.la_shop.la_men.Entities;
import jakarta.persistence.*;

@Entity
public class Moderation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long user_id, product_id;
    private String feedback_body;

    public Moderation() {
    }

    public Moderation(Long user_id, Long product_id, String feedback_body) {
        this.user_id = user_id;
        this.product_id = product_id;
        this.feedback_body = feedback_body;
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

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public String getFeedback_body() {
        return feedback_body;
    }

    public void setFeedback_body(String feedback_body) {
        this.feedback_body = feedback_body;
    }
}
