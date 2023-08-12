package com.api.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.api.entity.Product;
import com.api.helper.Helper;
import com.api.repo.ProductRepo;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private Helper helper;

    public void save(MultipartFile file) {

        try {
            List<Product> products = helper.convertExcelToListOfProduct(file.getInputStream());
            this.productRepo.saveAll(products);
            
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
//    @Async
//    public CompletableFuture<Void> save(MultipartFile file) {
//
//        try 
//        {
//        	
//        	CompletableFuture<List<Product>> future = helper.convertExcelToListOfProduct(file.getInputStream());
//            future.thenAccept(products -> productRepo.saveAll(products));
//            
//            
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//        
//    }
    
    public Product create(Product product) {
        
        int id = product.getproductId();
        Optional<Product> p = findById(id);
        Product newProduct = p.orElse(new Product());
        if(newProduct.getproductId() == null)
            return productRepo.save(product);
        else
        	throw new IllegalArgumentException("ID already exist");
        
    }
    
    public Optional<Product> findById(Integer id) {
        return productRepo.findById(id);
    }

    public Product update(Integer id, Product product) {
        
    	Optional<Product> existingProduct = productRepo.findById(id);
        if (existingProduct.isPresent()) {
            Product updatedProduct = existingProduct.get();
            updatedProduct.setproductName(product.getproductName());
            updatedProduct.setproductDesc(product.getproductDesc());
            updatedProduct.setproductPrice(product.getproductPrice());
            return productRepo.save(updatedProduct);
        } else {
            throw new IllegalArgumentException("Product not found with ID: " + id);
        }
    }
    
    public void delete(Integer id) {
        
    	Optional<Product> existingProduct = productRepo.findById(id);
        if (existingProduct.isPresent()) {
            productRepo.delete(existingProduct.get());
        } else {
            throw new IllegalArgumentException("Product not found with ID: " + id);
        }
    }

    
    public List<Product> getAllProducts() {
        return this.productRepo.findAll();
    }
//	@Async
//    public CompletableFuture<ByteArrayInputStream> validator(MultipartFile file) {
//	    	
//	    	
//			try {
//				
//				CompletableFuture<ByteArrayInputStream> future = helper.ValidateExcelData(file.getInputStream());
//				return future;
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				return CompletableFuture.completedFuture(null);
//			}
//	       
//	}
    public ByteArrayInputStream validator(MultipartFile file) {
    	
    	
    	ByteArrayInputStream byteArrayInputStream;
		try {
			
			byteArrayInputStream = helper.ValidateExcelData(file.getInputStream());
			return byteArrayInputStream;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return null;
    }

}
