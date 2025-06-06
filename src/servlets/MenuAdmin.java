package servlets;

import controllers.AdminController;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Usuario;

@WebServlet("/admin/dashboard")
public class MenuAdmin extends HttpServlet {
        private final AdminController controller = new AdminController();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        List<Usuario> usuarios = controller.listarUsuarios();
        req.setAttribute("usuarios", usuarios);
        req.getRequestDispatcher("/WEB-INF/views/admin/menuAdmin.jsp").forward(req, resp);
    }
    
    
}