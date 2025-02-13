document.addEventListener("DOMContentLoaded", async function () {

    // Función para obtener los productos desde el endpoint
    async function fetchProducts() {
        try {
            let response = await fetch('/products/all');
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
                    <img src="/img/pepe.jpg" alt="${product.name}" title="${product.name}" width="250px">
                </a>
                <h2>${product.name}</h2>
                <p>${product.description}</p>
                <p>Precio: $${product.price}</p>
                <p>6 Cuotas de <span>$${(product.price / 6).toFixed(2)}</span></p>

                <fieldset>
                    <legend>Elegí un tamaño</legend>
                    <label for="size-50-${product.id}">50 ML</label>
                    <input type="radio" value="50" name="size-${product.id}" id="size-50-${product.id}">
                    
                    <label for="size-100-${product.id}">100 ML</label>
                    <input type="radio" value="100" name="size-${product.id}" id="size-100-${product.id}">
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
    const products = await fetchProducts(); // Obtener productos
    renderProducts(products); // Renderizarlos en el HTML
});
