package dao;

import model.*;
import util.DBConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;


public class LoginDAO {


    private static final Logger logger = LogManager.getLogger(LoginDAO.class);

    public Usuario buscarPorUsuario(String nombreUsuario) {
        String sql = "SELECT * FROM usuarios WHERE usuario = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombreUsuario);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id_usuario");
                String usuario = rs.getString("usuario");
                String contrasena = rs.getString("contrasena");
                RolUsuario rol = RolUsuario.valueOf(rs.getString("rol"));
                boolean estado = rs.getBoolean("estado");

                if (!estado) {
                    logger.warn("Usuario desactivado: " + usuario);
                    return null;
                }

                return switch (rol) {
                    case ADMIN -> new Admin(id, usuario, contrasena, rol);
                    case PROFESOR -> new Profesor(id, usuario, contrasena, rol);
                    case ALUMNO -> new Alumno(id, usuario, contrasena, rol);
                };
            }

        } catch (SQLException e) {
            logger.error("Error al buscar usuario: " + nombreUsuario, e);
        }

        return null;
    }

    public boolean validarCredenciales(String inputContrasena, Usuario usuario) {
        return usuario != null && usuario.getContrasena().equals(inputContrasena);
    }

    //TODO: Hacer el recuperar Contraseña
}
