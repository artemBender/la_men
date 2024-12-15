package com.la_shop.la_men.Entities;

import jakarta.persistence.*;
import org.apache.tomcat.util.codec.binary.Base64;

@Entity
public class Products{
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private String name, description, composition;
private double price;
private Long catalog_sub_id;

    @Lob
    private byte[] image;
    public String generateBase64Image() {
        return Base64.encodeBase64String(this.image);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComposition() {
        return composition;
    }

    public void setComposition(String composition) {
        this.composition = composition;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Long getCatalog_sub_id() {
        return catalog_sub_id;
    }

    public void setCatalog_sub_id(Long catalog_sub_id) {
        this.catalog_sub_id = catalog_sub_id;
    }

    public byte[] getImage() {
        return image;
    }
    public void setImage(byte[] image) {
        this.image = image;
    }

    public Products() {
    }

    public Products(String name, String description, String composition, double price, Long catalog_sub_id, byte[] image) {
        this.name = name;
        this.description = description;
        this.composition = composition;
        this.price = price;
        this.catalog_sub_id = catalog_sub_id;
        this.image = image;
    }
}
