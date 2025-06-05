package servlets;

import controllers.AdminController;
import model.Usuario;
import model.Alumno;
import model.Profesor;
import model.RolUsuario;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/usuarios")
public class AdminUsuariosServlet extends HttpServlet {

    private final AdminController controller = new AdminController();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Usuario> usuarios = controller.listarUsuarios();
        req.setAttribute("usuarios", usuarios);
        req.getRequestDispatcher("/WEB-INF/admin/usuarios.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String accion = req.getParameter("accion");

        if ("crear".equals(accion)) {
            String usuario = req.getParameter("usuario");
            String contrasena = req.getParameter("contrasena");
            String rol = req.getParameter("rol");

            Usuario nuevoUsuario;
            if ("ALUMNO".equals(rol)) {
                nuevoUsuario = new Alumno(usuario, contrasena, RolUsuario.ALUMNO);
            } else if ("PROFESOR".equals(rol)) {
                nuevoUsuario = new Profesor(usuario, contrasena, RolUsuario.PROFESOR);
            } else {
                nuevoUsuario = new Alumno(usuario, contrasena, RolUsuario.ALUMNO);
            }

            controller.crearUsuario(nuevoUsuario);
            resp.sendRedirect("usuarios");
        } else if ("cambiar".equals(accion)) {
            int id = Integer.parseInt(req.getParameter("id"));
            String nuevaClave = req.getParameter("nuevaClave");
            controller.cambiarContrasena(id, nuevaClave);
            resp.sendRedirect("usuarios");
        } else if ("desactivar".equals(accion)) {
            int id = Integer.parseInt(req.getParameter("id"));
            controller.desactivarUsuario(id);
            resp.sendRedirect("usuarios");
        }
    }
}

