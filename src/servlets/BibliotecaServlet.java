package servlets;

import controllers.MaterialResumenController;
import model.MaterialResumen;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/biblioteca")
public class BibliotecaServlet extends HttpServlet {

    private final MaterialResumenController controller = new MaterialResumenController();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String termino = request.getParameter("termino") != null ? request.getParameter("termino") : "";
        String tipo = request.getParameter("tipo") != null ? request.getParameter("tipo") : "Todos";

        List<MaterialResumen> materiales = controller.buscarMateriales(termino, tipo);
        request.setAttribute("materiales", materiales);
        request.setAttribute("termino", termino);
        request.setAttribute("tipo", tipo);

        request.getRequestDispatcher("/WEB-INF/views/biblioteca.jsp").forward(request, response);
    }
}

 