package com.prueba.inventario.data;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CombinationResult {
    private List<String> productos;
    private BigDecimal suma;

    public CombinationResult(List<String> productos, BigDecimal suma) {
        this.productos = productos;
        this.suma = suma;
}
}
