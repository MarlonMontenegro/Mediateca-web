<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Configuración del Sistema</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container mt-5">
    <h3 class="mb-4">⚙️ Configuración del Sistema</h3>

    <!-- Mensajes -->
    <c:if test="${not empty mensaje}">
        <div class="alert alert-success">${mensaje}</div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>

    <!-- Formulario -->
    <form action="configuracion" method="post">
        <table class="table table-bordered bg-white shadow-sm">
            <thead class="table-dark">
                <tr>
                    <th>Tipo de Usuario</th>
                    <th>Ejemplares Máximos</th>
                    <th>Mora Diaria ($)</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="conf" items="${configuraciones}">
                    <tr>
                        <td>
                            ${conf.tipoUsuario}
                            <input type="hidden" name="id[]" value="${conf.id}" />
                            <input type="hidden" name="tipoUsuario[]" value="${conf.tipoUsuario}" />
                        </td>
                        <td>
                            <input type="number" name="ejemplaresMaximos[]" class="form-control" min="1" value="${conf.ejemplaresMaximos}" required />
                        </td>
                        <td>
                            <input type="number" name="moraDiaria[]" step="0.01" class="form-control" min="0" value="${conf.moraDiaria}" required />
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div class="d-flex justify-content-between">
<a href="${pageContext.request.contextPath}/admin/dashboard" class="btn btn-secondary">← Volver</a>
            <button type="submit" class="btn btn-primary">Guardar Cambios</button>
        </div>
    </form>
</div>

</body>
</html>
