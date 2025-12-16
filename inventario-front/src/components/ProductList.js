import React, { useEffect, useState } from "react";
import { getProducts, deleteProduct } from "../services/productService";

const ProductList = ({ onEdit }) => {
    const [products, setProducts] = useState([]);
    const [sortConfig, setSortConfig] = useState({ key: null, direction: "asc" });

    // Obtiene los productos
    const fetchProducts = async () => {
        const data = await getProducts();
        setProducts(data);
    };

    useEffect(() => {
        fetchProducts();
    }, []);

    //eliminar producto
    const handleDelete = async (id) => {
        await deleteProduct(id);
        fetchProducts();
    };

    // Cambiar la columna y dirección de orden
    const requestSort = (key) => {
        let direction = "asc";
        if (sortConfig.key === key && sortConfig.direction === "asc") {
            direction = "desc";
        }
        setSortConfig({ key, direction });
    };

    // Ordenar productos
    const sortedProducts = () => {
        let sortableProducts = [...products];
        if (sortConfig.key) {
            sortableProducts.sort((a, b) => {
                //paso todo a minuscula para un mejor ordenamiento
                const aValue = a[sortConfig.key]?.toString().toLowerCase();
                const bValue = b[sortConfig.key]?.toString().toLowerCase();

                if (aValue < bValue) return sortConfig.direction === "asc" ? -1 : 1;
                if (aValue > bValue) return sortConfig.direction === "asc" ? 1 : -1;
                return 0;
            });
        }
        return sortableProducts;
    };

    // Mostrar flecha de orden en el encabezado
    const getSortIndicator = (key) => {
        if (sortConfig.key === key) {
            return sortConfig.direction === "asc" ? " ▲" : " ▼";//flechas Alt + 30 - Alt + 31
        }
        return "";
    };

    return (
        <div>
            <h2>Lista de Productos</h2>
            <table>
                <thead>
                    <tr>
                        <th onClick={() => requestSort("name")}>Nombre{getSortIndicator("name")}</th>
                        <th onClick={() => requestSort("description")}>Descripción{getSortIndicator("description")}</th>
                        <th onClick={() => requestSort("price")}>Precio{getSortIndicator("price")}</th>
                        <th onClick={() => requestSort("ammount_stock")}>Stock{getSortIndicator("ammount_stock")}</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    {sortedProducts().map(p => (
                        <tr key={p.id}>
                            <td>{p.name}</td>
                            <td>{p.description}</td>
                            <td>{p.price}</td>
                            <td>{p.ammount_stock}</td>
                            <td>
                                <button onClick={() => onEdit(p)}>Editar</button>
                                <button onClick={() => handleDelete(p.id)}>Eliminar</button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default ProductList;
