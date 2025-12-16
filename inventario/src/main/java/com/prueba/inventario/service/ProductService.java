package com.prueba.inventario.service;

import java.math.BigDecimal;
import java.util.List;

import com.prueba.inventario.data.CombinationResult;
import com.prueba.inventario.data.ProductData;


public interface ProductService {
    ProductData createProduct(ProductData productData);

    ProductData getProductById(Long productId);

    List<ProductData> findAllProduct();

    ProductData updateProduct(Long productId, ProductData productData);

    void deleteProduct(Long productId);

    BigDecimal calculateTotalValueInv();

    List<CombinationResult> getCombinations (BigDecimal maxValue);
}
