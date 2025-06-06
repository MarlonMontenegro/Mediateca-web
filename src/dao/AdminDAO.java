package dao;

import model.*;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AdminDAO implements UserDAO {

    private static final Logger logger = LogManager.getLogger(CDDAO.class);


@Override
public boolean crearUsuario(Usuario usuario) {
    String sql = "INSERT INTO usuarios (usuario, contrasena, rol, estado) VALUES (?, ?, ?, ?)";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, usuario.getUsuario());
        stmt.setString(2, usuario.getContrasena());
        stmt.setString(3, usuario.getRol().name());
        stmt.setBoolean(4, usuario.isActivo()); // Nuevo

        stmt.executeUpdate();
        return true;

    } catch (SQLException e) {
        logger.error("Error al agregar un nuevo Usuario: " + e.getMessage(), e);
        return false;
    }
}

@Override
public Usuario obtenerPorId(int id) {
    String sql = "SELECT * FROM usuarios WHERE id_usuario = ?"; // Arreglado

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return obtenerUsuario(rs);
        }

    } catch (SQLException e) {
        logger.error("Error al obtener usuario por ID: " + e.getMessage(), e);
        throw new RuntimeException(e);
    }
    return null;
}

    @Override
    public List<Usuario> listarTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                usuarios.add(obtenerUsuario(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return usuarios;
    }

    @Override
    public boolean cambiarContrasena(int id, String nuevaClave) {
        String sql = "UPDATE usuarios SET contrasena = ? WHERE id_usuario = ?";

        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql))
        {
            //TODO: Hacer validacion de contraseña de la base de datos. Que no sea la misma

            stmt.setString(1, nuevaClave);
            stmt.setInt(2, id);
            stmt.executeUpdate();

            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean desactivarUsuario(int id) {

        String sql = "UPDATE usuarios SET estado = false WHERE id_usuario = ?";

        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    
@Override
public boolean activarUsuario(int id) {
    String sql = "UPDATE usuarios SET estado = true WHERE id_usuario = ?";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, id);
        stmt.executeUpdate();

        return true;

    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
}



private Usuario obtenerUsuario(ResultSet rs) throws SQLException {
    int id = rs.getInt("id_usuario");
    String usuario = rs.getString("usuario");
    String contrasena = rs.getString("contrasena");
    RolUsuario rol = RolUsuario.valueOf(rs.getString("rol"));
    boolean activo = rs.getBoolean("estado");

    Usuario user = switch (rol) {
        case ADMIN -> new model.Admin(id, usuario, contrasena, rol);
        case PROFESOR -> new model.Profesor(id, usuario, contrasena, rol);
        case ALUMNO -> new model.Alumno(id, usuario, contrasena, rol);
    };

    user.setActivo(activo); // Agregado
    return user;
}



@Override
public void actualizar(Usuario usuario) {
    String sql = "UPDATE usuarios SET usuario = ?, contrasena = ?, rol = ? WHERE id_usuario = ?";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, usuario.getUsuario());
        stmt.setString(2, usuario.getContrasena());
        stmt.setString(3, usuario.getRol().toString());
        stmt.setInt(4, usuario.getId());

        stmt.executeUpdate();

    } catch (SQLException e) {
        e.printStackTrace();
    }
}

}

