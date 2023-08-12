package com.api.service;

import java.io.ByteArrayInputStream;
import java.io.*;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.entity.NewProduct;
import com.api.helper.NewHelper;
import com.api.repo.NewProductRepo;

@Service
public class NewProductService {
	
	@Autowired
    private NewProductRepo repo;
	
	public ByteArrayInputStream getActualData() throws IOException{
		
		List<NewProduct>all = repo.findAll();
		ByteArrayInputStream byteArrayInputStream = NewHelper.dataToExcel(all);
        return byteArrayInputStream;
	} 
	
}
