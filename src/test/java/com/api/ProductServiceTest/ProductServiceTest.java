package com.api.ProductServiceTest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import com.api.entity.Product;
import com.api.helper.Helper;
import com.api.repo.ProductRepo;
import com.api.service.ProductService;

@SpringBootTest
public class ProductServiceTest {

    @Mock
    private ProductRepo productRepo;

    @Mock
    private Helper helper;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSave() throws IOException {
        // Create a mock MultipartFile
        MockMultipartFile file = new MockMultipartFile("test.xlsx", new byte[0]);

        // Create a mock list of products
        List<Product> products = new ArrayList<>();
        products.add(new Product(1, "Product 1", "Description 1", 10));

        // Mock the behavior of the helper method
        Mockito.when(helper.convertExcelToListOfProduct(Mockito.any())).thenReturn(products);

        // Perform the save operation
        productService.save(file);

        // Verify that the saveAll method was called with the correct list of products
        Mockito.verify(productRepo).saveAll(products);
    }

    @Test
    public void testCreate() {
        // Create a mock product
        Product product = new Product(1, "Product 1", "Description 1", 10);

        // Mock the behavior of the productRepo
        Mockito.when(productRepo.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        Mockito.when(productRepo.save(Mockito.any())).thenReturn(product);

        // Perform the create operation
        Product createdProduct = productService.create(product);

        // Verify that the productRepo's save method was called
        Mockito.verify(productRepo).save(product);

        // Verify that the returned product matches the mock product
        Assertions.assertEquals(product, createdProduct);
    }

    @Test
    public void testFindById() {
        // Create a mock product
        Product product = new Product(1, "Product 1", "Description 1", 10);

        // Mock the behavior of the productRepo
        Mockito.when(productRepo.findById(Mockito.anyInt())).thenReturn(Optional.of(product));

        // Perform the findById operation
        Optional<Product> foundProduct = productService.findById(1);

        // Verify that the productRepo's findById method was called
        Mockito.verify(productRepo).findById(1);

        // Verify that the returned product matches the mock product
        Assertions.assertEquals(Optional.of(product), foundProduct);
    }

    @Test
    public void testUpdate() {
        // Create a mock product
        Product existingProduct = new Product(3, "Product 3", "Description 3", 10);
        Product updatedProduct = new Product(3, "Updated Product", "Updated Description", 20);

        // Mock the behavior of the productRepo
        Mockito.when(productRepo.findById(Mockito.anyInt())).thenReturn(Optional.of(existingProduct));
        Mockito.when(productRepo.save(Mockito.any())).thenReturn(updatedProduct);

        // Perform the update operation
        Product result = productService.update(3, updatedProduct);

        // Verify that the productRepo's findById and save methods were called
        Mockito.verify(productRepo).findById(3);
        Mockito.verify(productRepo).save(updatedProduct);

        // Verify that the returned product matches the updated product
        Assertions.assertEquals(updatedProduct, result);
    }

    @Test
    public void testDelete() {
        // Create a mock product
        Product existingProduct = new Product(1, "Product 1", "Description 1", 10);

        // Mock the behavior of the productRepo
        Mockito.when(productRepo.findById(Mockito.anyInt())).thenReturn(Optional.of(existingProduct));

        // Perform the delete operation
        productService.delete(1);

        // Verify that the productRepo's delete method was called
        Mockito.verify(productRepo).delete(existingProduct);
    }

    @Test
    public void testGetAllProducts() {
        // Create a mock list of products
        List<Product> products = new ArrayList<>();
        products.add(new Product(1, "Product 1", "Description 1", 10));
        products.add(new Product(2, "Product 2", "Description 2", 20));

        // Mock the behavior of the productRepo
        Mockito.when(productRepo.findAll()).thenReturn(products);

        // Perform the getAllProducts operation
        List<Product> result = productService.getAllProducts();

        // Verify that the productRepo's findAll method was called
        Mockito.verify(productRepo).findAll();

        // Verify that the returned list of products matches the mock list
        Assertions.assertEquals(products, result);
    }

    @Test
    public void testValidator() throws IOException {
        // Create a mock MultipartFile
        MockMultipartFile file = new MockMultipartFile("test.xlsx", new byte[0]);

        // Create a mock ByteArrayInputStream
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(new byte[0]);

        // Mock the behavior of the helper method
        Mockito.when(helper.ValidateExcelData(Mockito.any())).thenReturn(byteArrayInputStream);

        // Perform the Validator operation
        //ByteArrayInputStream result = productService.Validator(file);

        // Verify that the helper's ValidateExcelData method was called
        Mockito.verify(helper).ValidateExcelData(file.getInputStream());

        // Verify that the returned ByteArrayInputStream matches the mock ByteArrayInputStream
        //Assertions.assertEquals(byteArrayInputStream, result);
    }
}
