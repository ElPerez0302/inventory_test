import React, { useState, useEffect } from "react";
import ProductList from "./components/ProductList";
import ProductForm from "./components/ProductForm";
import { getProducts } from "./services/productService";

function App() {
  const [editingProduct, setEditingProduct] = useState(null);
  const [refresh, setRefresh] = useState(false);
  const [products, setProducts] = useState([]);

  // Para "Sabias que"
  const [facts, setFacts] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [uselessFact, setUselessFact] = useState("");

  // obtiene products
  useEffect(() => {
    const fetchProducts = async () => {
      try {
        const data = await getProducts();
        setProducts(data);
      } catch (err) {
        console.error("Error al cargar productos:", err);
      }
    };
    fetchProducts();
  }, [refresh]);

  //obtiene datos de gatos al cargar la pag
  useEffect(() => {
    const fetchCatFacts = async () => {
      try {
        const res = await fetch("https://meowfacts.herokuapp.com/?lang=esp-mx");
        const data = await res.json();

        let fact1 = data.data[0] || "";
        let fact2 = data.data[1] || "";

        setFacts([fact1, fact2]);
        setShowModal(true); // mostrar modal al cargar
      } catch (err) {
        console.error("Error fetching cat facts:", err);
      }
    };
    
    fetchCatFacts();
  }, []);

  useEffect(() => {
    const fetchUselessFact = async () => {
      try {
        const res = await fetch("https://uselessfacts.jsph.pl/api/v2/facts/random?language=en");
        const data = await res.json();
        setUselessFact(data.text);
      } catch (err) {
        console.error("Error al obtener dato inútil:", err);
      }
    };
    fetchUselessFact();
  }, []);

  // Valor total del inventario
  const totalInventory = products.reduce(
    (sum, p) => sum + p.price * p.ammount_stock,
    0
  );

  // Producto con mayor valor de inventario
  const productWithMaxValue = products.reduce(
    (max, p) =>
      p.price * p.ammount_stock > max.price * max.ammount_stock ? p : max,
    products[0] || { name: "-", price: 0, ammount_stock: 0 }
  );

  const handleSave = () => {
    setEditingProduct(null);
    setRefresh(!refresh); // refrescar lista despés de guardar
  };

  return (
    <div>
      <h1>Inventario de Productos</h1>

      <div style={{ margin: "20px 0" }}>
        <h2>Total del Inventario: ${totalInventory}</h2>
        <h3>Producto con mayor valor de inventario:</h3>
        <p>
          {productWithMaxValue.name} - Valor: $
          {productWithMaxValue.price * productWithMaxValue.ammount_stock}
        </p>
      </div>

      <ProductForm
        product={editingProduct}
        onSave={handleSave}
        onCancel={() => setEditingProduct(null)}
      />

      <ProductList
        key={refresh}
        products={products}
        onEdit={setEditingProduct}
      />

      {/* Modal de "Sabias que" */}
      {showModal && (
        <div
          style={{
            position: "fixed",
            top: 0,
            left: 0,
            width: "100%",
            height: "100%",
            backgroundColor: "rgba(0,0,0,0.5)",
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
            zIndex: 1000,
          }}
        >
          <div
            style={{
              backgroundColor: "white",
              padding: "20px",
              borderRadius: "8px",
              maxWidth: "400px",
              textAlign: "center",
              position: "relative",
            }}
          >
            <h2>Sabías que...</h2>
            <ul style={{ textAlign: "left" }}>
              {facts.map((f, i) => (
                <li key={i}>{f}</li>
              ))}
            </ul>
            <button
              onClick={() => setShowModal(false)}
              style={{
                marginTop: "20px",
                padding: "8px 16px",
                borderRadius: "5px",
                border: "none",
                backgroundColor: "#007bff",
                color: "white",
                cursor: "pointer",
              }}
            >
              Cerrar
            </button>
          </div>
        </div>
      )}

      {/* dato inutil */}
      <footer style={{ marginTop: "auto", padding: "10px", background: "#f1f1f1", textAlign: "center" }}>
        <strong>Dato inútil del dia:</strong> {uselessFact}
      </footer>

    </div>
  );
}

export default App;
