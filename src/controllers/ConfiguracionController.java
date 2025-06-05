package controllers;

import dao.ConfiguracionDAO;
import model.ConfiguracionSistema;

public class ConfiguracionController {

    private final ConfiguracionDAO configuracionDAO = new ConfiguracionDAO();

    /**
     * Obtiene la configuración actual del sistema.
     * @return ConfiguracionSistema con los valores actuales o null si no existe.
     */
    public ConfiguracionSistema obtenerConfiguracion() {
        return configuracionDAO.obtenerConfiguracion();
    }

    /**
     * Actualiza la configuración del sistema.
     * @param config Objeto ConfiguracionSistema con los nuevos valores.
     * @return true si se actualizó correctamente, false en caso contrario.
     */
    public boolean actualizarConfiguracion(ConfiguracionSistema config) {
        if (config == null || config.getId() <= 0) {
            return false; // Validación simple
        }
        return configuracionDAO.actualizarConfiguracion(config);
    }
}