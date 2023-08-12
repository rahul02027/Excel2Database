package com.api.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Product {

    @Id
    private Integer product_id;

    private String product_name;

    private String product_desc;

    private Integer product_price;

    public Product(Integer product_id, String product_name, String product_desc, Integer product_price) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_desc = product_desc;
        this.product_price = product_price;
    }


    public Product() {
    }

    public Integer getproductId() {
        return product_id;
    }

    public void setproductId(Integer product_id) {
        this.product_id = product_id;
    }

    public String getproductName() {
        return product_name;
    }

    public void setproductName(String product_name) {
        this.product_name = product_name;
    }

    public String getproductDesc() {
        return product_desc;
    }

    public void setproductDesc(String product_desc) {
        this.product_desc = product_desc;
    }

    public Integer getproductPrice() {
        return product_price;
    }

    public void setproductPrice(Integer product_price) {
        this.product_price = product_price;
    }
}
