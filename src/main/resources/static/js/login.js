async function login(event) {
    event.preventDefault(); // Evita que la página se recargue

    const email = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    const response = await fetch("/auth/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/x-www-form-urlencoded",
      },
      body: new URLSearchParams({ email, password }),
      credentials: "include", // Esto permite que el navegador guarde la cookie
    });

    if (response.ok) {
      alert("Inicio de sesión exitoso");
      window.location.href = "/"; // Redirigir a la página principal
    } else {
      alert("Error en el inicio de sesión");
    }
  }    

  
const togglePassword = document.getElementById('togglePassword');
const passwordField = document.getElementById('password');
const passwordIcon = document.getElementById('passwordIcon');

togglePassword.addEventListener('click', () => {
    // Cambia el tipo del campo entre 'password' y 'text'
    const isPassword = passwordField.type === 'password';
    passwordField.type = isPassword ? 'text' : 'password';

    // Cambia la imagen
    passwordIcon.setAttribute('src', isPassword ? '/img/cerrar-ojo.png' : '/img/ojo.png');
});