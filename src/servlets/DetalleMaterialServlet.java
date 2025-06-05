package servlets;

import dao.CDDAO;
import dao.DVDDAO;
import dao.LibroDAO;
import dao.RevistaDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author marlo
 */
@WebServlet("/material/detalle")
public class DetalleMaterialServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter("id");
        String tipo = request.getParameter("tipo");

        Object material = null;

        switch (tipo.toUpperCase()) {
            case "LIBRO":
                material = new LibroDAO().buscarPorID(id);
                break;
            case "REVISTA":
                material = new RevistaDAO().buscarPorID(id);
                break;
            case "CD":
                material = new CDDAO().buscarPorID(id);
                break;
            case "DVD":
                material = new DVDDAO().buscarPorID(id);
                break;
        }

        request.setAttribute("material", material);
        request.setAttribute("tipo", tipo);
        request.getRequestDispatcher("/WEB-INF/views/detalle.jsp").forward(request, response);
    }
} 