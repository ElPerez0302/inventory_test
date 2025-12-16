package com.prueba.inventario.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prueba.inventario.data.CombinationResult;
import com.prueba.inventario.data.ProductData;
import com.prueba.inventario.service.ProductService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/producto")
public class ProductController {
    
    private ProductService productService;

    //construyo create product REST API
    @PostMapping
    public ResponseEntity<ProductData> createProduct(@RequestBody ProductData productData){
        ProductData productSaved = productService.createProduct(productData);
        return new ResponseEntity<>(productSaved, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductData> getProductById(@PathVariable("id") Long productId){
        ProductData productData = productService.getProductById(productId);
        return ResponseEntity.ok(productData);
    }

    @GetMapping
    public ResponseEntity<List<ProductData>> findAllProduct(){
        List<ProductData> products = productService.findAllProduct();
        return ResponseEntity.ok(products);
    }

    @PutMapping("{id}")
    public ResponseEntity<ProductData> updateProduct(@PathVariable("id") Long productId,
    @RequestBody ProductData updateProduct){
        ProductData productData = productService.updateProduct(productId,updateProduct);
        return ResponseEntity.ok(productData);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Long productId){
        productService.deleteProduct(productId);
        return ResponseEntity.ok("Producto eliminado!");
    }

    @GetMapping("/combinations")
    public ResponseEntity<List<CombinationResult>> getCombinations(@RequestParam("maxValue") BigDecimal maxValue) {
        List<CombinationResult> combinations = productService.getCombinations(maxValue);
        return ResponseEntity.ok(combinations);
}
}
