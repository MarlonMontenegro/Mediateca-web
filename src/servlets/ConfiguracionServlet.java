package servlets;

import controllers.ConfiguracionController;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.ConfiguracionSistema;

import java.io.IOException;
import java.util.List;

@WebServlet("/configuracion")
public class ConfiguracionServlet extends HttpServlet {

    private final ConfiguracionController controller = new ConfiguracionController();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<ConfiguracionSistema> configuraciones = controller.obtenerTodas();
        request.setAttribute("configuraciones", configuraciones);
        request.getRequestDispatcher("/WEB-INF/views/admin/configuracion.jsp").forward(request, response);
    }

    
    @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    String[] ids = request.getParameterValues("id[]");
    String[] tipos = request.getParameterValues("tipoUsuario[]");
    String[] ejemplares = request.getParameterValues("ejemplaresMaximos[]");
    String[] moras = request.getParameterValues("moraDiaria[]");

    boolean exito = true;

    for (int i = 0; i < ids.length; i++) {
        try {
            int id = Integer.parseInt(ids[i]);
            String tipo = tipos[i];
            int ejemplaresMax = Integer.parseInt(ejemplares[i]);
            double mora = Double.parseDouble(moras[i]);

            ConfiguracionSistema config = new ConfiguracionSistema(id, tipo, ejemplaresMax, mora);
            if (!controller.actualizarConfiguracion(config)) {
                exito = false;
            }

        } catch (Exception e) {
            exito = false;
            request.setAttribute("error", "Error procesando los datos: " + e.getMessage());
            break;
        }
    }

    if (exito) {
        request.setAttribute("mensaje", "Configuraciones actualizadas correctamente.");
    } else {
        request.setAttribute("error", "Ocurrió un error al actualizar.");
    }

    doGet(request, response); // recarga la vista con datos actualizados
}

    
    
    
}
