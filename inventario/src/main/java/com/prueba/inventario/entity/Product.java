package com.prueba.inventario.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "producto")
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

     @Column(name = "name", nullable = false)
     private String name;

     @Column(name = "description")
     private String description;

     @Column(name = "price", precision = 10, scale = 2, nullable = false)
     private BigDecimal price;

     @Column(name = "ammount_stock", nullable = false)
     private int ammount_stock;
}
