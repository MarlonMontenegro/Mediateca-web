package servlets;

import controllers.ConfiguracionController;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.ConfiguracionSistema;

import java.io.IOException;

@WebServlet("/configuracion")
public class ConfiguracionServlet extends HttpServlet {

    private final ConfiguracionController controller = new ConfiguracionController();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ConfiguracionSistema config = controller.obtenerConfiguracion();
        request.setAttribute("configuracion", config);
        request.getRequestDispatcher("/configuracion.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            int ejemplaresMaximos = Integer.parseInt(request.getParameter("ejemplaresMaximos"));
            double moraDiaria = Double.parseDouble(request.getParameter("moraDiaria"));

            ConfiguracionSistema config = new ConfiguracionSistema(id, ejemplaresMaximos, moraDiaria);
            boolean actualizado = controller.actualizarConfiguracion(config);

            if (actualizado) {
                request.setAttribute("mensaje", "Configuración actualizada correctamente.");
            } else {
                request.setAttribute("error", "No se pudo actualizar la configuración.");
            }

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Error en el formato de los datos.");
        }

        doGet(request, response);
    }
}
