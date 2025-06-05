<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Sistema de Biblioteca</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" />
    <style>
        .badge-libro { background-color: #0d6efd; }
        .badge-revista { background-color: #198754; }
        .badge-cd { background-color: #6f42c1; }
        .badge-dvd { background-color: #dc3545; }
    </style>
</head>
<body class="bg-light">
<div class="container mt-4">
    <h1 class="mb-3">Sistema de Biblioteca</h1>
    <div class="d-flex justify-content-between align-items-center mb-3">
    <p class="text-muted mb-0">
        Busca y explora nuestra colección de libros, CDs, revistas y DVDs
    </p>
<a href="login" class="btn btn-outline-primary">Iniciar sesión</a>
</div>

    <!-- Búsqueda -->
    <form class="row g-3 mb-4" method="get" action="biblioteca">
        <div class="col-md-6">
<input type="text" name="termino" class="form-control" placeholder="Título o Autor" value="${termino}">
        </div>
        <div class="col-md-3">
            <select class="form-select" name="tipo">
<option value="Todos" ${tipo == 'Todos' ? 'selected' : ''}>Todos los tipos</option>
<option value="LIBRO" ${tipo == 'LIBRO' ? 'selected' : ''}>Libro</option>
<option value="REVISTA" ${tipo == 'REVISTA' ? 'selected' : ''}>Revista</option>
<option value="CD" ${tipo == 'CD' ? 'selected' : ''}>CD</option>
<option value="DVD" ${tipo == 'DVD' ? 'selected' : ''}>DVD</option>
            </select>
        </div>
        <div class="col-md-3">
            <button type="submit" class="btn btn-dark w-100">Buscar</button>
        </div>
    </form>

    <!-- Resultados -->
    <h5 class="mb-3">
        Resultados de búsqueda 
        <c:if test="${not empty materiales}">
            (${materiales.size()} encontrados)
        </c:if>
    </h5>

    <div class="row g-4">
        <c:forEach var="mat" items="${materiales}">
            <div class="col-md-4">
                <div class="card h-100 shadow-sm">
                    <div class="card-body">
                        <div class="d-flex justify-content-between mb-2">
                            <span class="badge 
                                <c:choose>
                                    <c:when test="${mat.tipo == 'LIBRO'}">badge-libro</c:when>
                                    <c:when test="${mat.tipo == 'REVISTA'}">badge-revista</c:when>
                                    <c:when test="${mat.tipo == 'CD'}">badge-cd</c:when>
                                    <c:when test="${mat.tipo == 'DVD'}">badge-dvd</c:when>
                                    <c:otherwise>bg-secondary</c:otherwise>
                                </c:choose> text-white">
                                ${mat.tipo}
                            </span>
                            <span class="badge 
                                ${mat.unidadesDisponibles > 0 ? 'bg-dark' : 'bg-danger'}">
                                ${mat.unidadesDisponibles > 0 ? 'Disponible' : 'No disponible'}
                            </span>
                        </div>
                        <h5 class="card-title">${mat.titulo}</h5>
                        <p class="card-text">
                            <strong>Código:</strong> ${mat.codigoIdentificacion} <br>
                            <strong>Disponibles:</strong> ${mat.unidadesDisponibles}
                        </p>
<a href="material/detalle?id=${mat.codigoIdentificacion}&tipo=${mat.tipo}" class="btn btn-outline-primary btn-sm">Ver detalles</a>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>

    <c:if test="${empty materiales}">
        <div class="alert alert-warning mt-4">No hay materiales para mostrar.</div>
    </c:if>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
