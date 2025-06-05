<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestión de Usuarios</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
</head>
<body class="bg-light">

<!-- Navbar -->
<nav class="navbar navbar-light bg-white shadow-sm px-4 py-2 d-flex justify-content-between align-items-center">
    <div>
        <span class="fw-bold">Bienvenido, ${usuario.usuario}</span><br>
        <small class="text-muted">Rol: ${usuario.rol}</small>
    </div>
    <a href="${pageContext.request.contextPath}/logout" class="btn btn-dark">
        <i class="bi bi-box-arrow-right me-2"></i> Cerrar Sesión
    </a>
</nav>

<div class="container mt-4">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h4>Usuarios del sistema</h4>
        <div>
            <a href="${pageContext.request.contextPath}/configuracion" class="btn btn-outline-secondary me-2">
                <i class="bi bi-gear"></i> Configuración
            </a>
            <a href="${pageContext.request.contextPath}/admin/usuarios?form=nuevo" class="btn btn-primary">
                <i class="bi bi-person-plus-fill"></i> Agregar Usuario
            </a>
        </div>
    </div>

    <!-- Tabla -->
    <div class="table-responsive bg-white shadow rounded">
        <table class="table table-hover align-middle mb-0">
            <thead class="table-dark">
                <tr>
                    <th>#</th>
                    <th>Usuario</th>
                    <th>Rol</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="u" items="${usuarios}" varStatus="s">
                    <tr>
                        <td>${s.index + 1}</td>
                        <td>${u.usuario}</td>
                        <td>${u.rol}</td>
                        <td>
                            <form action="usuarios" method="post" style="display:inline;">
                                <input type="hidden" name="accion" value="desactivar">
                                <input type="hidden" name="id" value="${u.id}">
                                <button type="submit" class="btn btn-sm btn-danger">Eliminar</button>
                            </form>
                            <a href="usuarios.jsp?edit=${u.id}" class="btn btn-sm btn-warning">Editar</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <div class="mt-4">
        <a href="${pageContext.request.contextPath}/admin/dashboard" class="btn btn-secondary">← Volver</a>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
