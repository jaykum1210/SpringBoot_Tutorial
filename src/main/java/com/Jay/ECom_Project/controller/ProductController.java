package com.Jay.ECom_Project.controller;


import com.Jay.ECom_Project.model.ProductDetails;
import com.Jay.ECom_Project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping("/")
    public String greet(){
        return "Hello!";
    }

    @RequestMapping("/products")
    public ResponseEntity<List<ProductDetails>> getAllProductsDetails(){
        return new ResponseEntity<>(productService.getAllProductsDetails(), HttpStatus.OK);
    }

    @RequestMapping("/product/{ProId}")
    public ResponseEntity<ProductDetails> getProductDetailById(@PathVariable int ProId){
        ProductDetails productDetails = productService.getProductDetailById(ProId);

        if (productDetails!=null){
            return new ResponseEntity<>(productDetails,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(
            @RequestPart("product") ProductDetails productDetails,
            @RequestPart("imageFile") MultipartFile imageFile) {

        try {
            ProductDetails savedProduct =
                    productService.addProduct(productDetails, imageFile);

            return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(
                    e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @RequestMapping("/product/{ProdId}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable int ProdId){
        ProductDetails productDetails = productService.getProductDetailById(ProdId);
        byte[] imageFile = productDetails.getImageData();
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(productDetails.getImageType()))
                .body(productDetails.getImageData());
    }

    @PutMapping("/product/{ProId}")
    public ResponseEntity<String> updateProductDetails(
            @PathVariable int ProId,
            @RequestPart ProductDetails productDetails,
            @RequestPart MultipartFile imageFile
    ) throws IOException {
        ProductDetails productDetails1 = productService.updateProductDetails(ProId,productDetails,imageFile);

        if (productDetails1!=null){
            return new ResponseEntity<>("Product Details Updated",HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("Product Details Not Updated", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("product/{ProId}")
    public ResponseEntity<String> deleteProduct(@PathVariable int ProId){
        ProductDetails productDetails = productService.getProductDetailById(ProId);

        if (productDetails!=null){
            productService.productDetails(ProId);
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Not Found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("products/search")
    public ResponseEntity<List<ProductDetails>> searchProducts(@RequestParam String keyword) {
        System.out.println("Hello " + keyword);

        List<ProductDetails> productDetails = productService.searchProducts(keyword);

        if (productDetails != null && !productDetails.isEmpty()) {
            return new ResponseEntity<>(productDetails, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
