package com.api.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.api.entity.Product;
import com.api.helper.Helper;
import com.api.service.ProductService; 

@RestController
@CrossOrigin("*")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/product/upload")
    @Async
    public CompletableFuture<ResponseEntity<?>> upload(@RequestParam("file") MultipartFile file) {

    	
        if (Helper.checkExcelFormat(file)) {
            //true

            this.productService.save(file);

            return CompletableFuture.completedFuture(ResponseEntity.ok(Map.of("message", "File is uploaded and data is saved to db")))
                    .thenApplyAsync(response -> {
                        ResponseEntity<Resource> downloadResponse;
						try {
							downloadResponse = download(file);
							return downloadResponse;
						} catch (IOException e) {
							e.printStackTrace();
						}
                        return null;
                        
                    });

        }
        return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload excel file "));
    }
//    @PostMapping("/product/upload")
//    @Async
//    public CompletableFuture<ResponseEntity<?>> upload(@RequestParam("file") MultipartFile file) {
//        if (Helper.checkExcelFormat(file)) {
//            //true
//            CompletableFuture<ResponseEntity<?>> saveFuture = productService.save(file);
//            CompletableFuture<ResponseEntity<?>> downloadFuture = saveFuture.thenCompose(responseEntity -> productService.download(file));
//            return downloadFuture;
//        }
//        return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload an Excel file"));
//    }


     
    @GetMapping("/product")
    public List<Product> getAllProduct() {
        return this.productService.getAllProducts();
    }
    
    
    
    
    @PostMapping("/addproduct")
    public Product create(@RequestBody Product product) {
        return productService.create(product);
    }

    @GetMapping("/findproduct/{id}")
    public Optional<Product> findById(@PathVariable Integer id) {
        return productService.findById(id);
    }


    @PutMapping("/update/{id}")
    public Product update(@PathVariable Integer id, @RequestBody Product product) {
        product.setproductId(id);
        return productService.update(id, product);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Integer id) {
        productService.delete(id);
        
    
    }
    
    
    @RequestMapping("/doownload")
    public ResponseEntity<Resource> download(@RequestParam("file") MultipartFile file) throws IOException{
		
		String filename = "ProductReport.xlsx";
		
		InputStream actualData = productService.validator(file);
		InputStreamResource Reportfile = new InputStreamResource(actualData);
		
		ResponseEntity<Resource> body = ResponseEntity.ok()
			.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+filename)
			.contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
			.body(Reportfile);
		
		return body;
    }
    
//    @RequestMapping("/doownload")
//    public CompletableFuture<ResponseEntity<?>> download(MultipartFile file) throws IOException {
//        return helper.validateExcelData(file.getInputStream())
//                .thenApplyAsync(actualData -> {
//                    if (actualData != null) {
//                        InputStreamResource reportFile = new InputStreamResource(actualData);
//						HttpHeaders headers = new HttpHeaders();
//						headers.setContentDispositionFormData("attachment", "ProductReport.xlsx");
//						headers.setContentType(MediaType.parseMediaType("application/vnd.ms-excel"));
//						return ResponseEntity.ok().headers(headers).body(reportFile);
//                    }
//                    return ResponseEntity.notFound().build();
//                });
//    }

    
    
}
