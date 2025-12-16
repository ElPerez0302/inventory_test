package com.prueba.inventario.data;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductData {
    private long id;
    private String name;
    private String description;
    private BigDecimal price;
    private int ammount_stock;
}
