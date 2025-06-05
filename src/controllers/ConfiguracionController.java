package controllers;

import dao.ConfiguracionDAO;
import model.ConfiguracionSistema;

import java.util.List;

public class ConfiguracionController {

    private final ConfiguracionDAO configuracionDAO = new ConfiguracionDAO();

    /**
     * Obtiene todas las configuraciones del sistema (por tipo de usuario).
     * @return Lista de ConfiguracionSistema.
     */
    public List<ConfiguracionSistema> obtenerTodas() {
        return configuracionDAO.obtenerTodas();
    }

    /**
     * Actualiza la configuración del sistema.
     * @param config Objeto ConfiguracionSistema con los nuevos valores.
     * @return true si se actualizó correctamente, false en caso contrario.
     */
    public boolean actualizarConfiguracion(ConfiguracionSistema config) {
        if (config == null || config.getId() <= 0) {
            return false; // Validación básica
        }
        return configuracionDAO.actualizarConfiguracion(config);
    }
}
