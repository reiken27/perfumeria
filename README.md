# Documentación del Proyecto E-Commerce para Práctica Profesional - Grupo Carhué

## 1. Introducción
Este proyecto es un sistema de e-commerce desarrollado en Java como parte de la practica profesional de la carrera de Tecnicatura en Programación.

El sistema permite la gestión de productos, usuarios, compras y pagos, contando además con un carrito de compras. Se han implementado servicios de autenticación y seguridad utilizando JWT, y se ha utilizado HTMX para mejorar la interacción con la base de datos desde el frontend.

## 1.1 Cómo ejecutar el proyecto
- Tener MySQL instalado y corriendo
- Importar perfumeria.sql en la base de datos para cargar la estructura y datos iniciales.
- Modificar el archivo application.properties con sus credenciales:
    spring.datasource.username=
    spring.datasource.password=
- Compilar y ejecutar proyecto con: mvn spring-boot:run
- Acceder a la API:
    http://localhost:8080


---
## 2. Tecnologías Utilizadas
- **Backend:** Java, Spring Boot
- **Base de Datos:** MySQL (utilizada para almacenar productos y la gestión de autenticación)
- **Frontend:** HTML, CSS, JavaScript, HTMX
- **Seguridad:** JWT (JSON Web Tokens)

---
## 3. Módulos del Sistema
- **Gestión de Productos**: Administración de productos con filtros de búsqueda.
- **Usuarios**: Registro, autenticación y gestión de usuarios.
- **Compras**: Implementación de carrito de compras y procesos de checkout.
- **Pagos**: Simulación de procesamiento de pagos.

---
## 4. Base de Datos
La base de datos se utiliza para almacenar información sobre los productos y la autenticación de usuarios. Además, se ha implementado la funcionalidad de guardar las imágenes en la base de datos con el nombre del archivo para facilitar su carga en la página mediante HTMX.

---
## 5. Endpoints y API
### 5.1. ProductController
**URL Base:** `/products`

- **Obtener Productos con Filtros**
  - `GET /products`
  - Filtra por marca, nombre, categoría, precio mínimo y máximo.

- **Obtener Producto por ID**
  - `GET /products/{id}`
  - Retorna un producto según su ID.

- **Crear Producto**
  - `POST /products`
  - Agrega un nuevo producto.

- **Actualizar Producto**
  - `PUT /products/{id}`
  - Modifica un producto existente.

- **Eliminar Producto**
  - `DELETE /products/{id}`
  - Elimina un producto por su ID.

- **Listar Productos en la Vista**
  - `GET /products/productos`
  - Devuelve una lista de productos para la vista.

- **Listar Productos Recomendados**
  - `GET /products/recomendados`
  - Retorna productos aleatorios.

- **Filtrar Perfumes por Género**
  - `GET /products/woman` (perfumes de mujer)
  - `GET /products/man` (perfumes de hombre)
  - `GET /products/all` (todos los perfumes)

---
### 5.2. UserController
**URL Base:** `/users`

- **Obtener Usuario Autenticado**
  - `GET /users/me`
  - Retorna la información del usuario autenticado.

- **Obtener Todos los Usuarios**
  - `GET /users`
  - Devuelve una lista de todos los usuarios registrados.

---
### 5.3. ShoppingCartController
**URL Base:** `/cart`

- **Obtener Carrito**
  - `GET /cart`
  - Devuelve el contenido del carrito de compras.

- **Agregar Producto al Carrito**
  - `POST /cart/add`
  - Agrega un producto al carrito.

- **Eliminar Producto del Carrito**
  - `DELETE /cart/remove/{productId}`
  - Elimina un producto específico del carrito.

- **Vaciar Carrito**
  - `DELETE /cart/clear`
  - Vacía el carrito de compras.

- **Finalizar Compra**
  - `POST /cart/checkout`
  - Simula el proceso de compra y vacía el carrito.

---
### 5.4. ImageUploadController
**URL Base:** `/api/images`

- **Subir Imagen**
  - `POST /api/images/upload`
  - Permite la carga de imágenes en el sistema.

---
### 5.5. ImageServeController
**URL Base:** `/api/images`

- **Obtener Imagen**
  - `GET /api/images/{fileName}`
  - Devuelve la imagen almacenada en la carpeta de `uploads`.

---
### 5.6. FrontController
**URL Base:** `/`

- `GET /` → Renderiza la página principal (`index.html`).  
- `GET /login` → Renderiza el formulario de inicio de sesión.  
- `GET /signup` → Renderiza el formulario de registro.  
- `GET /carrito` → Muestra la vista del carrito de compras.  
- `GET /item/{id}` → Muestra la vista de un producto en detalle.  

---
### 5.7. AuthenticationController
**URL Base:** `/auth`

- `POST /auth/signup` → Registra un nuevo usuario.  
- `POST /auth/login` → Autentica un usuario y devuelve un token JWT.  
- `GET /auth/logout` → Cierra sesión eliminando el JWT almacenado en cookies.

---
### 5.8. AdminFront
**URL Base:** `/admin`

- `GET /admin/addItem` → Renderiza la vista para agregar productos como administrador.

---
## 6. GlobalExceptionHndler
Implementamos un manejador global de excepciones (GlobalExceptionHandler) para asegurarnos de que, cuando algo falle, la aplicación responda de manera controlada en lugar de simplemente dejar de funcionar. Esta clase atrapa los errores que pueden surgir en diferentes situaciones y nos permite devolver respuestas estructuradas con información útil en lugar de un mensaje genérico o un error sin formato.

Funcionamiento:
Cada vez que se lanza una excepción dentro de la aplicación, este manejador la detecta y devuelve una respuesta HTTP con detalles como la fecha y hora del error, el código de estado correspondiente (como 400, 403 o 500), y un mensaje explicando qué pasó. De esta forma, el frontend o cualquier cliente que consuma la API puede entender mejor qué salió mal.

Tipos de errores que manejamos:
Hay varios tipos de excepciones que tratamos en esta clase. Por ejemplo, si un usuario intenta autenticarse con credenciales incorrectas, lanzamos un BadCredentialsException y devolvemos un código 401 - No autorizado. Si el usuario tiene la cuenta bloqueada, el error cambia a AccountStatusException con un 403 - Prohibido. También cubrimos problemas de seguridad con JWT, como tokens expirados o firmas inválidas.

Por otro lado, tenemos excepciones más específicas para el carrito de compras. Si hay un problema con una operación en el carrito, devolvemos un 400 - Solicitud incorrecta con un mensaje detallado. Lo mismo hacemos si un producto no existe en la base de datos (404 - No encontrado) o si los datos del producto no son válidos (400 - Solicitud incorrecta).

¿Por qué hicimos esto?
Sin este sistema, cada error mostraría un mensaje técnico generico o, peor aún, la aplicación podría crashear sin avisar qué pasó. Al manejar las excepciones de esta manera, logramos respuestas más claras para los usuarios y, al mismo tiempo, evitamos llenar el código de los controladores con bloques try-catch repetitivos. Además, al registrar los errores (exception.printStackTrace()), podemos hacer un mejor seguimiento de los problemas en la aplicación.

---
## 7. Seguridad y Autenticación
- **JWT:** Utilizado para la autenticación y autorización de usuarios.
- **Spring Security:** Implementado para proteger los endpoints del sistema.

---
## 8. Unit Testing
Hicimos pruebas unitarias para los Controllers y el Service, asi nos aseguramos que los endpoints y la lógica de negocio funcionen correctamente. Para los Controllers, usamos @Mock para simular las dependencias (como los services o repositorios) y @InjectMocks para instanciar los controladores sin necesidad de levantar todo el contexto de Spring. También usamos MockMvc en algunos casos para probar los endpoints como si fueran peticiones HTTP reales.
En todos los tests usamos Mockito y JUnit para simular dependencias y verificar que los métodos sean llamados con los parámetros correctos. Nos aseguramos de que cada funcionalidad clave estuviera cubierta y respondiera como se espera en cada escenario posible.

---
## 9. Conclusión
Este sistema de e-commerce permite gestionar productos, usuarios y compras de manera eficiente, utilizando distintas tecnologías en backend y frontend. La integración con HTMX y la gestión de imágenes en la base de datos mejoran la experiencia del usuario en la plataforma.

