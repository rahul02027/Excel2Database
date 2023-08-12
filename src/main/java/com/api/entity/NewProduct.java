package com.api.entity;

import javax.persistence.*;

@Entity
@Table(name="product")
public class NewProduct {
    @Id
    @Column(name = "product_id")
    private Integer id;

    @Column(name = "product_name")
    private String name;

	@Column(name = "product_desc")
    private String description;
    
    @Column(name = "product_price")
    private Integer  price;
    
    public NewProduct() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}
	
}
