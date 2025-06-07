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

/**
 * Servlet para gestionar usuarios desde el panel de administración.
 * Permite listar, crear, editar, activar/desactivar usuarios y cambiar contraseñas.
 */
@WebServlet("/admin/usuarios")
public class AdminUsuariosServlet extends HttpServlet {

    private final AdminController controller = new AdminController();

    /**
     * Maneja solicitudes GET.
     * Puede mostrar el formulario de creación, edición o el listado de usuarios.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String form = req.getParameter("form");
        String edit = req.getParameter("edit");

        // Mostrar formulario para crear nuevo usuario
        if ("nuevo".equals(form)) {
            req.setAttribute("modo", "crear");
            req.getRequestDispatcher("/WEB-INF/views/admin/formUsuario.jsp").forward(req, resp);
            return;
        }

        // Mostrar formulario para editar un usuario existente
        if (edit != null) {
            try {
                int id = Integer.parseInt(edit);
                Usuario usuarioEditar = controller.obtenerUsuarioPorId(id);
                if (usuarioEditar != null) {
                    req.setAttribute("usuarioEditar", usuarioEditar);
                    req.setAttribute("modo", "editar");
                    req.getRequestDispatcher("/WEB-INF/views/admin/formUsuario.jsp").forward(req, resp);
                    return;
                } else {
                    req.setAttribute("error", "Usuario no encontrado.");
                }
            } catch (NumberFormatException e) {
                req.setAttribute("error", "ID inválido para edición.");
            }
        }

        // Mostrar lista de todos los usuarios
        List<Usuario> usuarios = controller.listarUsuarios();
        req.setAttribute("usuarios", usuarios);
        req.getRequestDispatcher("/WEB-INF/views/admin/usuarios.jsp").forward(req, resp);
    }

    /**
     * Maneja solicitudes POST.
     * Procesa acciones como crear, editar, cambiar contraseña, activar o desactivar usuarios.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String accion = req.getParameter("accion");

        // Crear nuevo usuario
        if ("crear".equals(accion)) {
            String usuario = req.getParameter("usuario");
            String contrasena = req.getParameter("contrasena");
            String rol = req.getParameter("rol");

            Usuario nuevoUsuario;
            if ("ALUMNO".equalsIgnoreCase(rol)) {
                nuevoUsuario = new Alumno(usuario, contrasena, RolUsuario.ALUMNO);
            } else if ("PROFESOR".equalsIgnoreCase(rol)) {
                nuevoUsuario = new Profesor(usuario, contrasena, RolUsuario.PROFESOR);
            } else {
                nuevoUsuario = new model.Admin(usuario, contrasena, RolUsuario.ADMIN);
            }

            controller.crearUsuario(nuevoUsuario);
            resp.sendRedirect("usuarios");

        // Editar usuario existente
        } else if ("editar".equals(accion)) {
            int id = Integer.parseInt(req.getParameter("id"));
            String usuario = req.getParameter("usuario");
            String contrasena = req.getParameter("contrasena");
            String rol = req.getParameter("rol");

            Usuario usuarioEditado;
            if ("ALUMNO".equalsIgnoreCase(rol)) {
                usuarioEditado = new Alumno(id, usuario, contrasena, RolUsuario.ALUMNO);
            } else if ("PROFESOR".equalsIgnoreCase(rol)) {
                usuarioEditado = new Profesor(id, usuario, contrasena, RolUsuario.PROFESOR);
            } else {
                usuarioEditado = new model.Admin(id, usuario, contrasena, RolUsuario.ADMIN);
            }

            controller.actualizarUsuario(usuarioEditado);
            resp.sendRedirect("usuarios");

        // Cambiar contraseña de un usuario
        } else if ("cambiar".equals(accion)) {
            int id = Integer.parseInt(req.getParameter("id"));
            String nuevaClave = req.getParameter("nuevaClave");
            controller.cambiarContrasena(id, nuevaClave);
            resp.sendRedirect("usuarios");

        // Desactivar usuario
        } else if ("desactivar".equals(accion)) {
            int id = Integer.parseInt(req.getParameter("id"));
            controller.desactivarUsuario(id);
            resp.sendRedirect("usuarios");

        // Activar usuario
        } else if ("activar".equals(accion)) {
            int id = Integer.parseInt(req.getParameter("id"));
            controller.activarUsuario(id);
            resp.sendRedirect("usuarios");
        }
    }
}
