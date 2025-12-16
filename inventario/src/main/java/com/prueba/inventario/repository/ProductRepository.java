package com.prueba.inventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.prueba.inventario.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}
