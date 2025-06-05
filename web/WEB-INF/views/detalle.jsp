<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <title>Detalles del Material</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container mt-4">
    <h2>Detalles del Material</h2>
    <c:if test="${not empty material}">
        <ul class="list-group">
            <li class="list-group-item"><strong>Título:</strong> ${material.titulo}</li>
            <li class="list-group-item"><strong>Código:</strong> ${material.codigoIdentificacion}</li>
            <li class="list-group-item"><strong>Disponibles:</strong> ${material.unidadesDisponibles}</li>

            <c:if test="${tipo == 'LIBRO'}">
                <li class="list-group-item"><strong>Autor:</strong> ${material.autor}</li>
                <li class="list-group-item"><strong>Año:</strong> ${material.anioPublicacion}</li>
            </c:if>

            <c:if test="${tipo == 'CD'}">
                <li class="list-group-item"><strong>Duración:</strong> ${material.duracion}</li>
                <li class="list-group-item"><strong>Artista:</strong> ${material.artista}</li>
            </c:if>

            <!-- Podés agregar más info según el tipo -->
        </ul>
    </c:if>
</div>
</body>
</html>
