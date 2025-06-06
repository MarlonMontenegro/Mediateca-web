<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>${modo == 'editar' ? 'Editar Usuario' : 'Nuevo Usuario'}</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="bg-light">

        <div class="container mt-5">
            <div class="card shadow-sm">
                <div class="card-header ${modo == 'editar' ? 'bg-warning' : 'bg-primary'} text-white">
                    <h4 class="mb-0">
                        ${modo == 'editar' ? '✏️ Editar Usuario' : '➕ Crear Nuevo Usuario'}
                    </h4>
                </div>
                <div class="card-body">
                    <form action="${pageContext.request.contextPath}/admin/usuarios" method="post">
                        <input type="hidden" name="accion" value="${modo == 'editar' ? 'editar' : 'crear'}" />

                        <c:if test="${modo == 'editar'}">
                            <input type="hidden" name="id" value="${usuarioEditar.id}" />
                        </c:if>

                        <div class="mb-3">
                            <label for="usuario" class="form-label">Usuario</label>
                            <input type="text" class="form-control" id="usuario" name="usuario"
                                   required value="${modo == 'editar' ? usuarioEditar.usuario : ''}" />
                        </div>

                        <div class="mb-3">
                            <label for="contrasena" class="form-label">Contraseña</label>
                            <input type="password" class="form-control" id="contrasena" name="contrasena"
                                   required value="${modo == 'editar' ? usuarioEditar.contrasena : ''}" />
                        </div>

                        <div class="mb-3">
                            <label for="rol" class="form-label">Rol</label>
                            <select class="form-select" id="rol" name="rol" required>
                                <option value="ALUMNO" ${usuarioEditar.rol == 'ALUMNO' ? 'selected' : ''}>Alumno</option>
                                <option value="PROFESOR" ${usuarioEditar.rol == 'PROFESOR' ? 'selected' : ''}>Profesor</option>
                                <option value="ADMIN" ${usuarioEditar.rol == 'ADMIN' ? 'selected' : ''}>Administrador</option>
                            </select>
                        </div>

                        <button type="submit" class="btn btn-${modo == 'editar' ? 'warning' : 'success'}">
                            ${modo == 'editar' ? 'Actualizar Usuario' : 'Crear Usuario'}
                        </button>
                        <a href="${pageContext.request.contextPath}/admin/usuarios" class="btn btn-secondary">Cancelar</a>
                    </form>
                </div>
            </div>
        </div>

    </body>
</html>
