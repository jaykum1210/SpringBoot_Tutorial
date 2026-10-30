package com.Jay.ECom_Project.service;

import com.Jay.ECom_Project.model.ProductDetails;
import com.Jay.ECom_Project.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    public List<ProductDetails> getAllProductsDetails(){
        return productRepo.findAll();
    }

    public ProductDetails getProductDetailById(int ProId){
        return productRepo.findById(ProId).orElse(null);
    }

    public ProductDetails addProduct(ProductDetails Productdetail, MultipartFile imageFile) throws IOException {
        Productdetail.setImageName(imageFile.getOriginalFilename());
        Productdetail.setImageType(imageFile.getContentType());
        Productdetail.setImageData(imageFile.getBytes());

        return productRepo.save(Productdetail);
    }

    public ProductDetails updateProductDetails(int ProId, ProductDetails productDetails, MultipartFile imageFile) throws IOException {
        productDetails.setImageData(imageFile.getBytes());
        productDetails.setImageType(imageFile.getContentType());
        productDetails.setImageName(imageFile.getOriginalFilename());
        return productRepo.save(productDetails);
    }

    public void productDetails(int ProId){
        productRepo.deleteById(ProId);
    }

    public List<ProductDetails> searchProducts(String keyword){
        return productRepo.searchProduct(keyword);
    }
}
