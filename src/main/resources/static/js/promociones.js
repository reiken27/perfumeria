document.addEventListener("DOMContentLoaded", async function () {
    // Función para aplicar descuento y almacenar el precio original
    function applyDiscount(products, discount) {
        return products.map(product => {
            product.originalPrice = product.price;
            product.price = (product.price * (1 - discount)).toFixed(2); // Redondear a 2 decimales
            return product;
        });
    }

    // Función para obtener los productos desde el endpoint
    async function fetchProducts() {
        try {
            let response = await fetch('/products/promociones');
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            let products = await response.json();
            return products;
        } catch (error) {
            console.error('Error al obtener los productos:', error);
            return [];
        }
    }

    // Función para renderizar los productos en el HTML
    function renderProducts(products) {
        const container = document.getElementById("product-list"); // Contenedor donde mostrar los productos
        container.innerHTML = ""; // Limpiar el contenido anterior

        products.forEach(product => {
            let article = document.createElement("article");

            article.innerHTML = `
                <a href="http://localhost:8080/item/${product.id}">
                    <img src="localhost:8080/api/images/${product.image}" alt="${product.name}" title="${product.name}" width="250px">
                </a>
                <h2>${product.name}</h2>
                <p>${product.description}</p>
                <p>Precio Original: <s>$${product.originalPrice}</s></p>
                <p>Precio con Descuento: <strong>$${product.price}</strong></p>
                <p>6 Cuotas de <span>$${(product.price / 6).toFixed(2)}</span></p>

                <fieldset>
                    <legend>Elegí un tamaño</legend>
                    <div class="options-container">
                        <label th:for="'size-50-' + ${product.id}" th:text="'50 ML'"></label>
                        <input type="radio" value="50" th:name="'size-' + ${product.id}" th:id="'size-50-' + ${product.id}">
                        
                        <label th:for="'size-100-' + ${product.id}" th:text="'100 ML'"></label>
                        <input type="radio" value="100" th:name="'size-' + ${product.id}" th:id="'size-100-' + ${product.id}">
                    </div>
                </fieldset>
                
                <form class="add-to-cart-form">
                    <input type="hidden" name="productId" value="${product.id}">
                    <input type="number" name="quantity" value="1" min="1" max="100">
                    <button type="submit">Comprar</button>
                </form>
            `;

            container.appendChild(article);
        });
    }

    // Obtener los productos, aplicar el descuento y renderizar
    let products = await fetchProducts();
    if (products.length > 0) {
        let discountedProducts = applyDiscount(products, 0.3);
        renderProducts(discountedProducts);
    }
});
