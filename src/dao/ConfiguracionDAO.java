package dao;

import model.ConfiguracionSistema;
import util.DBConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class ConfiguracionDAO {

    private static final Logger logger = LogManager.getLogger(ConfiguracionDAO.class);


    public ConfiguracionSistema obtenerConfiguracion() {
        String sql = "SELECT * FROM ConfiguracionSistema WHERE id = 1";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                int id = rs.getInt("id");
                int ejemplaresMaximos = rs.getInt("ejemplares_maximos");
                double moraDiaria = rs.getDouble("mora_diaria");

                return new ConfiguracionSistema(id, ejemplaresMaximos, moraDiaria);
            }

        } catch (SQLException e) {
            logger.error("Error al obtener la configuración del sistema", e);
        }

        return null;
    }

    public boolean actualizarConfiguracion(ConfiguracionSistema config) {
        String sql = "UPDATE ConfiguracionSistema SET ejemplares_maximos = ?, mora_diaria = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, config.getEjemplaresMaximos());
            stmt.setDouble(2, config.getMoraDiaria());
            stmt.setInt(3, config.getId());

            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            logger.error("Error al actualizar la configuración del sistema", e);
            return false;
        }
    }


}
