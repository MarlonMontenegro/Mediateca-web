package servlets;

import controllers.MaterialResumenController;
import model.MaterialResumen;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

/**
 * Servlet encargado de gestionar la vista pública de la biblioteca.
 * Permite consultar materiales por tipo y por término de búsqueda.
 */
@WebServlet("/biblioteca")
public class BibliotecaServlet extends HttpServlet {

    // Controlador para interactuar con los materiales resumidos (libros, revistas, etc.)
    private final MaterialResumenController controller = new MaterialResumenController();

    /**
     * Maneja solicitudes GET para mostrar los materiales disponibles.
     * Permite filtrar por término de búsqueda y por tipo de material.
     *
     * @param request  objeto que contiene los parámetros de la solicitud
     * @param response objeto para enviar la respuesta al cliente
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtener el término de búsqueda si está presente, o usar cadena vacía por defecto
        String termino = request.getParameter("termino") != null ? request.getParameter("termino") : "";

        // Obtener el tipo de material (Libro, Revista, etc.) o "Todos" si no se especifica
        String tipo = request.getParameter("tipo") != null ? request.getParameter("tipo") : "Todos";

        // Buscar materiales filtrando por término y tipo
        List<MaterialResumen> materiales = controller.buscarMateriales(termino, tipo);

        // Guardar los datos en el request para que estén disponibles en el JSP
        request.setAttribute("materiales", materiales);
        request.setAttribute("termino", termino);
        request.setAttribute("tipo", tipo);

        // Redirigir a la vista JSP de la biblioteca
        request.getRequestDispatcher("/WEB-INF/views/biblioteca.jsp").forward(request, response);
    }
}
