<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Iniciar Sesión - Mediateca</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #eef2f7;
        }
        .login-card {
            max-width: 400px;
            margin: auto;
        }
        .icon-circle {
            width: 60px;
            height: 60px;
            background-color: #0d6efd;
            color: white;
            font-size: 30px;
            display: flex;
            align-items: center;
            justify-content: center;
            border-radius: 50%;
            margin: auto;
        }
    </style>
</head>
<body class="d-flex align-items-center justify-content-center vh-100">

<div>
    <!-- Logo + Título -->
    <div class="text-center mb-4">
        <div class="icon-circle mb-3">
            <i class="bi bi-book"></i> <!-- si usás Bootstrap Icons -->
        </div>
        <h2>Sistema de Biblioteca</h2>
        <p class="text-muted">Inicia sesión para acceder al catálogo</p>
    </div>

    <!-- Formulario -->
    <div class="card shadow login-card p-4">
        <h4 class="text-center mb-3">Iniciar Sesión</h4>
        <p class="text-muted text-center mb-3">Ingresa tus credenciales para continuar</p>

        <% String error = (String) request.getAttribute("error");
           if (error != null) { %>
            <div class="alert alert-danger text-center"><%= error %></div>
        <% } %>

        <form action="login" method="post">
            <div class="mb-3">
                <label for="usuario" class="form-label">Usuario</label>
                <input type="text" class="form-control" id="usuario" name="usuario" required>
            </div>
            <div class="mb-3">
                <label for="contrasena" class="form-label">Contraseña</label>
                <input type="password" class="form-control" id="contrasena" name="contrasena" required>
            </div>
            <button type="submit" class="btn btn-dark w-100">Iniciar Sesión</button>
        </form>
    </div>

    <!-- Usuarios de demostración -->
    <div class="card mt-4 p-3">
        <h6 class="text-primary">Usuarios de Demostración</h6>
        <ul class="list-group list-group-flush">
            <li class="list-group-item">
                <strong>Administrador</strong><br>
                Usuario: admin<br>Contraseña: 1234
            </li>
            <li class="list-group-item">
                <strong>Profesor</strong><br>
                Usuario: profesor<br>Contraseña: 1234
            </li>
            <li class="list-group-item">
                <strong>alumno</strong><br>
                Usuario: alumno<br>Contraseña: 1234
            </li>
        </ul>
    </div>
</div>

<!-- Bootstrap JS opcional -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>