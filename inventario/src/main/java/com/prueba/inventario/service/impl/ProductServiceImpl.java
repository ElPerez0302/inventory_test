package com.prueba.inventario.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.prueba.inventario.data.CombinationResult;
import com.prueba.inventario.data.ProductData;
import com.prueba.inventario.entity.Product;
import com.prueba.inventario.exceptions.NotFoundException;
import com.prueba.inventario.mapper.ProductMapper;
import com.prueba.inventario.repository.ProductRepository;
import com.prueba.inventario.service.ProductService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    @Override
    public ProductData createProduct(ProductData productData){

        Product product = ProductMapper.mapToProduct(productData);
        Product productSaved = productRepository.save(product);

        return ProductMapper.mapToProductData(productSaved);
    }

    @Override
    public ProductData getProductById(Long productId){

        Product product = productRepository.findById(productId)
        .orElseThrow(() -> new NotFoundException("No se encontró registro con id: " + productId));

        return ProductMapper.mapToProductData(product);
    }

    @Override
    public List<ProductData> findAllProduct(){
        List<Product> products = productRepository.findAll();
        return products.stream().map(product -> ProductMapper.mapToProductData(product)).collect(Collectors.toList());
    }

     @Override
    public ProductData updateProduct(Long productId, ProductData productData){

        Product product = productRepository.findById(productId)
        .orElseThrow(() -> new NotFoundException("No se encontró registro con id: " + productId));

        product.setName(productData.getName());
        product.setDescription(productData.getDescription());
        product.setPrice(productData.getPrice());
        product.setAmmount_stock(productData.getAmmount_stock());

        Product updateProduct = productRepository.save(product);

        return ProductMapper.mapToProductData(updateProduct);
    }

    @Override
    public void deleteProduct(Long productId){
        productRepository.findById(productId).orElseThrow(() -> new NotFoundException("No se encontró registro con id: " + productId));

        productRepository.deleteById(productId);
    }

    @Override
    public BigDecimal calculateTotalValueInv(){
         List<Product> products = productRepository.findAll();
        BigDecimal total = BigDecimal.ZERO;

        for(Product p : products){
            BigDecimal ammount = BigDecimal.valueOf(p.getAmmount_stock());
            total = total.add(p.getPrice().multiply(ammount));
        }
        return total;
    }

    @Override
    public List<CombinationResult> getCombinations (BigDecimal maxValue){
        List<Product> productos = productRepository.findAll();
        List<CombinationResult> combinaciones = new ArrayList<>();

        int n = productos.size();

        // Combinaciones de 2 productos
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                BigDecimal suma = productos.get(i).getPrice().add(productos.get(j).getPrice());
                if (suma.compareTo(maxValue) <= 0) {
                    combinaciones.add(new CombinationResult(
                            List.of(productos.get(i).getName(), productos.get(j).getName()),
                            suma
                    ));
                }
            }
        }

        // Combinaciones de 3 productos
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    BigDecimal suma = productos.get(i).getPrice()
                            .add(productos.get(j).getPrice())
                            .add(productos.get(k).getPrice());
                    if (suma.compareTo(maxValue) <= 0) {
                        combinaciones.add(new CombinationResult(
                                List.of(productos.get(i).getName(), productos.get(j).getName(), productos.get(k).getName()),
                                suma
                        ));
                    }
                }
            }
        }

        // Ordenar descendentemente por la suma
        combinaciones.sort((c1, c2) -> c2.getSuma().compareTo(c1.getSuma()));

        // Tomar maximo 5 elementos
        return combinaciones.stream().limit(5).collect(Collectors.toList());
    }
}
