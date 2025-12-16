package com.prueba.inventario.mapper;

import com.prueba.inventario.data.ProductData;
import com.prueba.inventario.entity.Product;

public class ProductMapper {
    
    public static ProductData mapToProductData(Product product){
        return new ProductData(
            product.getId(),
            product.getName(),
            product.getDescription(),
            product.getPrice(),
            product.getAmmount_stock()
        );

    }

    public static Product mapToProduct(ProductData productData){
        return new Product(
            productData.getId(),
            productData.getName(),
            productData.getDescription(),
            productData.getPrice(),
            productData.getAmmount_stock()
        );
    }
}
