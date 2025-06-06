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
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    ${mensaje}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Cerrar"></button>
                </div>
            </c:if>
            <c:if test="${not empty error}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    ${error}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Cerrar"></button>
                </div>
            </c:if>

            <!-- Formulario -->
            <form action="configuracion" method="post" class="bg-white p-4 rounded shadow-sm">
                <div class="row g-4">
                    <c:forEach var="conf" items="${configuraciones}">
                        <div class="col-md-6">
                            <div class="border rounded p-3 h-100">
                                <h5 class="text-primary">${conf.tipoUsuario}</h5>
                                <input type="hidden" name="id[]" value="${conf.id}" />
                                <input type="hidden" name="tipoUsuario[]" value="${conf.tipoUsuario}" />

                                <div class="mb-3">
                                    <label class="form-label">Ejemplares Máximos</label>
                                    <input type="number" name="ejemplaresMaximos[]" class="form-control" min="1" value="${conf.ejemplaresMaximos}" required />
                                </div>

                                <div>
                                    <label class="form-label">Mora Diaria ($)</label>
                                    <input type="number" name="moraDiaria[]" step="0.01" class="form-control" min="0" value="${conf.moraDiaria}" required />
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <div class="d-flex justify-content-between mt-4">
                    <a href="${pageContext.request.contextPath}/admin/dashboard" class="btn btn-secondary">← Volver</a>
                    <button type="submit" class="btn btn-primary">Guardar Cambios</button>
                </div>
            </form>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
