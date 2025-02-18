document.addEventListener("htmx:afterRequest", function (event) {
  document.querySelectorAll('.add-to-cart-form').forEach(form => {
    console.log("salfdmjmafoi");
    form.addEventListener('submit', async event => {
      console.log("salfdmjmafoi");
      event.preventDefault();

      const formData = new FormData(form);
      const jsonData = {};

      formData.forEach((value, key) => {
        jsonData[key] = value;
      });





      try {
        const response = await fetch('/cart/add', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(jsonData)
        });

        if (response.ok) {
          const result = await response.json();
          document.querySelector("#cart-container").innerHTML = `
                    <p>Producto agregado al carrito: ${result.productName || 'Producto'}</p>
                `;
        } else {
          document.querySelector("#cart-container").innerHTML = `
                    <p>Error al agregar el producto</p>
                `;
        }
      } catch (error) {
        console.error("Error en la solicitud:", error);
        document.querySelector("#cart-container").innerHTML = `
                <p>Error en la solicitud</p>
            `;
      }
    });
  });

});