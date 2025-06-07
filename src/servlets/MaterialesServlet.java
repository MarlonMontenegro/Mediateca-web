package servlets;

import controllers.*;
import model.*;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/materiales")
public class MaterialesServlet extends HttpServlet {

    private final LibroController libroController = new LibroController();
    private final DVDController dvdController = new DVDController();
    private final CDController cdController = new CDController();
    private final RevistaController revistaController = new RevistaController();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String tipo = request.getParameter("tipo");
        boolean exito = false;

        try {
            switch (tipo) {
                case "libro" -> {
                    Libro libro = new Libro(
                            request.getParameter("codigoIdentificacion"),
                            request.getParameter("titulo"),
                            Integer.parseInt(request.getParameter("unidadesDisponibles")),
                            request.getParameter("editorial"),
                            request.getParameter("autor"),
                            Integer.parseInt(request.getParameter("numeroPaginas")),
                            request.getParameter("isbn"),
                            request.getParameter("anioPublicacion")
                    );
                    exito = libroController.agregarLibro(libro);
                }
                case "dvd" -> {
                    DVD dvd = new DVD(
                            request.getParameter("codigoIdentificacion"),
                            request.getParameter("titulo"),
                            Integer.parseInt(request.getParameter("unidadesDisponibles")),
                            request.getParameter("genero"),
                            request.getParameter("duracion"),
                            request.getParameter("director")
                    );
                    exito = dvdController.agregarDVD(dvd);
                }
                case "cd" -> {
                    CDdeAudio cd = new CDdeAudio(
                            request.getParameter("codigoIdentificacion"),
                            request.getParameter("titulo"),
                            Integer.parseInt(request.getParameter("unidadesDisponibles")),
                            request.getParameter("genero"),
                            request.getParameter("duracion"),
                            request.getParameter("artista"),
                            Integer.parseInt(request.getParameter("numeroCanciones"))
                    );
                    exito = cdController.agregarCD(cd);
                }
                case "revista" -> {
                    Revista revista = new Revista(
                            request.getParameter("codigoIdentificacion"),
                            request.getParameter("titulo"),
                            Integer.parseInt(request.getParameter("unidadesDisponibles")),
                            request.getParameter("editorial"),
                            request.getParameter("periodicidad"),
                            request.getParameter("fechaPublicacion")
                    );
                    exito = revistaController.agregarRevista(revista);
                }
            }
        } catch (Exception e) {
            request.setAttribute("error", "Error al procesar el formulario: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/admin/formMaterial.jsp").forward(request, response);
            return;
        }

        if (exito) {
            response.sendRedirect(request.getContextPath() + "/admin/dashboard?mensaje=materialAgregado");
        } else {
            request.setAttribute("error", "No se pudo agregar el material.");
            request.getRequestDispatcher("/WEB-INF/views/admin/formMaterial.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String form = request.getParameter("form");

        if ("nuevo".equals(form)) {
            request.getRequestDispatcher("/WEB-INF/views/admin/formMaterial.jsp").forward(request, response);
        } else {
            response.sendRedirect("admin");
        }
    }
}
