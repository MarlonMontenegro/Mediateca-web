package dao;

import model.ConfiguracionSistema;
import util.DBConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConfiguracionDAO {

    private static final Logger logger = LogManager.getLogger(ConfiguracionDAO.class);

    // ✅ Obtener todas las configuraciones (alumno, profesor, etc.)
    public List<ConfiguracionSistema> obtenerTodas() {
        List<ConfiguracionSistema> lista = new ArrayList<>();
        String sql = "SELECT * FROM ConfiguracionSistema";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                ConfiguracionSistema conf = new ConfiguracionSistema(
                        rs.getInt("id"),
                        rs.getString("tipo_usuario"),
                        rs.getInt("ejemplares_maximos"),
                        rs.getDouble("mora_diaria")
                );
                lista.add(conf);
            }

        } catch (SQLException e) {
            logger.error("Error al obtener configuraciones del sistema", e);
        }

        return lista;
    }

    // ✅ Actualizar configuración por ID
    public boolean actualizarConfiguracion(ConfiguracionSistema config) {
        String sql = "UPDATE ConfiguracionSistema SET ejemplares_maximos = ?, mora_diaria = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, config.getEjemplaresMaximos());
            stmt.setDouble(2, config.getMoraDiaria());
            stmt.setInt(3, config.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            logger.error("Error al actualizar configuración del sistema", e);
            return false;
        }
    }
}
