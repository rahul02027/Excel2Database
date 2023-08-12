package com.api.controller;


import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.service.NewProductService;
import org.springframework.http.HttpHeaders;
import org.springframework.core.io.Resource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@CrossOrigin("*")
@RequestMapping("/newproduct")
public class NewProductController {
	
	@Autowired
	private NewProductService service;
	
	@RequestMapping("/excel")
	public ResponseEntity<Resource> download() throws IOException{
		
		String filename = "NewProduct.xlsx";
		
		ByteArrayInputStream actualData = service.getActualData();
		InputStreamResource file = new InputStreamResource(actualData);
		
		ResponseEntity<Resource> body = ResponseEntity.ok()
			.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+filename)
			.contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
			.body(file);
		return body;
	}
}
