<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dashboard Admin</title>
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
            <a href="usuarios?form=nuevo" class="btn btn-primary">
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
                    <th>Estado</th>
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
                            <span class="badge ${u.activo ? 'bg-success' : 'bg-danger'}">
                                ${u.activo ? 'Activo' : 'Inactivo'}
                            </span>
                        </td>
                        <td>
                            <c:if test="${u.activo}">
                                <form action="usuarios" method="post" style="display:inline;">
                                    <input type="hidden" name="accion" value="desactivar">
                                    <input type="hidden" name="id" value="${u.id}">
                                    <button type="submit" class="btn btn-sm btn-danger">
                                        <i class="bi bi-x-circle"></i> Eliminar
                                    </button>
                                </form>
                            </c:if>
                            <a href="usuarios.jsp?edit=${u.id}" class="btn btn-sm btn-warning">
                                <i class="bi bi-pencil-square"></i> Editar
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
