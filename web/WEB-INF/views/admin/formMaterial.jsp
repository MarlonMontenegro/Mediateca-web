<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Agregar Material</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <script>
        function mostrarCamposPorTipo() {
            const tipo = document.getElementById("tipo").value;
            const secciones = document.querySelectorAll(".seccion-material");
            secciones.forEach(s => s.style.display = "none");
            if (tipo)
                document.getElementById("seccion-" + tipo).style.display = "block";
        }
    </script>
</head>
<body class="bg-light">

<div class="container mt-5">
    <div class="card shadow-sm">
        <div class="card-header bg-success text-white">
            <h4 class="mb-0">Agregar Material</h4>
        </div>
        <div class="card-body">
            <form action="materiales" method="post">
                <input type="hidden" name="accion" value="crear" />

                <div class="mb-3">
                    <label for="tipo" class="form-label">Tipo de Material</label>
                    <select class="form-select" id="tipo" name="tipo" required onchange="mostrarCamposPorTipo()">
                        <option value="">Seleccione...</option>
                        <option value="libro">Libro</option>
                        <option value="revista">Revista</option>
                        <option value="dvd">DVD</option>
                        <option value="cd">CD de Audio</option>
                    </select>
                </div>

                <div class="mb-3">
                    <label for="codigoIdentificacion" class="form-label">Código de Identificación</label>
                    <input type="text" class="form-control" id="codigoIdentificacion" name="codigoIdentificacion" required />
                </div>

                <div class="mb-3">
                    <label for="titulo" class="form-label">Título</label>
                    <input type="text" class="form-control" id="titulo" name="titulo" required />
                </div>

                <div class="mb-3">
                    <label for="unidadesDisponibles" class="form-label">Unidades Disponibles</label>
                    <input type="number" class="form-control" id="unidadesDisponibles" name="unidadesDisponibles" required />
                </div>

                <!-- LIBRO -->
                <div id="seccion-libro" class="seccion-material" style="display:none;">
                    <div class="mb-3">
                        <label>Editorial</label>
                        <input type="text" class="form-control" name="editorial" />
                    </div>
                    <div class="mb-3">
                        <label>Autor</label>
                        <input type="text" class="form-control" name="autor" />
                    </div>
                    <div class="mb-3">
                        <label>Número de Páginas</label>
                        <input type="number" class="form-control" name="numeroPaginas" />
                    </div>
                    <div class="mb-3">
                        <label>ISBN</label>
                        <input type="text" class="form-control" name="isbn" />
                    </div>
                    <div class="mb-3">
                        <label>Año de Publicación</label>
                        <input type="text" class="form-control" name="anioPublicacion" />
                    </div>
                </div>

                <!-- REVISTA -->
                <div id="seccion-revista" class="seccion-material" style="display:none;">
                    <div class="mb-3">
                        <label>Editorial</label>
                        <input type="text" class="form-control" name="editorial" />
                    </div>
                    <div class="mb-3">
                        <label>Periodicidad</label>
                        <input type="text" class="form-control" name="periodicidad" />
                    </div>
                    <div class="mb-3">
                        <label>Fecha de Publicación</label>
                        <input type="date" class="form-control" name="fechaPublicacion" />
                    </div>
                </div>

                <!-- DVD -->
                <div id="seccion-dvd" class="seccion-material" style="display:none;">
                    <div class="mb-3">
                        <label>Género</label>
                        <input type="text" class="form-control" name="genero" />
                    </div>
                    <div class="mb-3">
                        <label>Duración</label>
                        <input type="text" class="form-control" name="duracion" />
                    </div>
                    <div class="mb-3">
                        <label>Director</label>
                        <input type="text" class="form-control" name="director" />
                    </div>
                </div>

                <!-- CD -->
                <div id="seccion-cd" class="seccion-material" style="display:none;">
                    <div class="mb-3">
                        <label>Género</label>
                        <input type="text" class="form-control" name="genero" />
                    </div>
                    <div class="mb-3">
                        <label>Duración</label>
                        <input type="text" class="form-control" name="duracion" />
                    </div>
                    <div class="mb-3">
                        <label>Artista</label>
                        <input type="text" class="form-control" name="artista" />
                    </div>
                    <div class="mb-3">
                        <label>Número de Canciones</label>
                        <input type="number" class="form-control" name="numeroCanciones" />
                    </div>
                </div>

                <button type="submit" class="btn btn-success">Guardar</button>
                 <a href="${pageContext.request.contextPath}/admin/usuarios" class="btn btn-secondary">Cancelar</a>

            </form>
        </div>
    </div>
</div>

</body>
</html>
