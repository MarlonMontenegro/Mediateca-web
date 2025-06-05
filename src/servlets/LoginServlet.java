package servlets;

import controllers.LoginController;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Usuario;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final LoginController loginController = new LoginController();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String usuarioInput = request.getParameter("usuario");
        String contrasenaInput = request.getParameter("contrasena");

        Usuario usuario = loginController.validar(usuarioInput, contrasenaInput);

        if (usuario != null) {
            HttpSession session = request.getSession();
            session.setAttribute("usuario", usuario);

            // Ir al servlet de menú, que redirige según el rol
            response.sendRedirect(usuario.redirect());
        } else {
            request.setAttribute("error", "Usuario o contraseña inválidos");
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }
}



