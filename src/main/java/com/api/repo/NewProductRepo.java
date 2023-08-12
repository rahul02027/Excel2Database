package com.api.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.entity.NewProduct;

public interface NewProductRepo extends JpaRepository<NewProduct,Integer>{
	
}
