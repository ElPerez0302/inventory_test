import React, { useState, useEffect } from "react";
import { createProduct, updateProduct } from "../services/productService";

const ProductForm = ({ product, onSave, onCancel }) => {
    const [formData, setFormData] = useState({
        name: "",
        description: "",
        price: 0,
        ammount_stock: 0
    });

    useEffect(() => {
        if (product) setFormData(product);
    }, [product]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prev => ({ ...prev, [name]: value }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (product) {
            await updateProduct(product.id, formData);
        } else {
            await createProduct(formData);
        }
        onSave();

        //resetea los valores
         setFormData({
            name: "",
            description: "",
            price: 0,
            ammount_stock: 0
    });
    };

    const handleCancel = () => {
        setFormData({
            name: "",
            description: "",
            price: 0,
            ammount_stock: 0
    });
  onCancel();
};

    return (
        <form onSubmit={handleSubmit}>
            <input name="name" value={formData.name} onChange={handleChange} placeholder="Nombre" required />
            <input name="description" value={formData.description} onChange={handleChange} placeholder="Descripción" />
            <input name="price" type="number" step="0.01" value={formData.price} onChange={handleChange} placeholder="Precio" required />
            <input name="ammount_stock" type="number" value={formData.ammount_stock} onChange={handleChange} placeholder="Stock" required />
            <button type="submit">{product ? "Actualizar" : "Agregar"}</button>
            <button type="button" onClick={handleCancel}>Cancelar</button>
        </form>
    );
};

export default ProductForm;
