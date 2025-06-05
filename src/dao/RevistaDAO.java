package dao;

import model.Revista;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.DBConnection;

public class RevistaDAO implements MaterialDAO<Revista> {

    private static final Logger logger = LogManager.getLogger(RevistaDAO.class);

    /**
     * Inserta una nueva revista en la base de datos.
     * Se registran los datos tanto en la tabla 'Material' como en 'Revista' dentro de una transacción.
     *
     * @param revista Objeto Revista con los datos a guardar.
     * @return true si el registro se realizó correctamente, false si ocurrió algún error.
     */
    @Override
    public boolean agregar(Revista revista) {
        String insertMaterial = "INSERT INTO Material (id_material, titulo, unidades_disponibles, id_tipo) VALUES (?, ?, ?, ?)";
        String insertRevista = "INSERT INTO Revista (id_material, editorial, periodicidad, fecha_publicacion) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmtMaterial = conn.prepareStatement(insertMaterial);
                 PreparedStatement stmtRevista = conn.prepareStatement(insertRevista)) {

                // Insertar en material
                stmtMaterial.setString(1, revista.getCodigoIdentificacion());
                stmtMaterial.setString(2, revista.getTitulo());
                stmtMaterial.setInt(3, revista.getUnidadesDisponibles());
                stmtMaterial.setInt(4, 2);

                stmtMaterial.executeUpdate();

                //insertar en Revista
                stmtRevista.setString(1, revista.getCodigoIdentificacion());
                stmtRevista.setString(2, revista.getEditorial());
                stmtRevista.setString(3, revista.getPeriodicidad());
                stmtRevista.setString(4, revista.getFechaPublicacion());

                stmtRevista.executeUpdate();

                conn.commit();
                return true;

            } catch (SQLException e) {
                conn.rollback();
                logger.error("Error al insertar revista: " + e.getMessage(), e);
                return false;
            }

        } catch (SQLException e) {
            logger.error("Error de conexión al insertar revista: " + e.getMessage(), e);
            return false;
        }
    }

    /**
     * Actualiza los datos de una revista existente en la base de datos.
     * Se actualizan tanto los datos generales de la tabla material como los detalles específicos de la revista.
     *
     * @param revista Objeto Revista con los datos actualizados.
     * @return true si la actualización se realizó correctamente, false si ocurrió algún error.
     */
    @Override
    public boolean actualizar(Revista revista) {
        String updateMaterial = "UPDATE Material SET titulo = ?, unidades_disponibles = ? WHERE id_material = ?";
        String updateRevista = "UPDATE Revista SET editorial = ?, periodicidad = ?, fecha_publicacion = ? WHERE id_material = ?";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmtMaterial = conn.prepareStatement(updateMaterial);
                 PreparedStatement stmtRevista = conn.prepareStatement(updateRevista)) {

                stmtMaterial.setString(1, revista.getTitulo());
                stmtMaterial.setInt(2, revista.getUnidadesDisponibles());
                stmtMaterial.setString(3, revista.getCodigoIdentificacion());
                stmtMaterial.executeUpdate();

                stmtRevista.setString(1, revista.getEditorial());
                stmtRevista.setString(2, revista.getPeriodicidad());
                stmtRevista.setDate(3, java.sql.Date.valueOf(revista.getFechaPublicacion()));
                stmtRevista.setString(4, revista.getCodigoIdentificacion());
                stmtRevista.executeUpdate();

                conn.commit();
                return true;

            } catch (SQLException e) {
                conn.rollback();
                logger.error("Error al actualizar revista: " + e.getMessage(), e);
                return false;
            }

        } catch (SQLException e) {
            logger.error("Error de conexión al actualizar revista: " + e.getMessage(), e);
            return false;
        }
    }

    /**
     * Elimina una revista de la base de datos a partir de su ID.
     * Elimina el registro en 'Material' y, como tiene de la relación ON DELETE CASCADE,
     * también elimina en la tabla 'Revista'.
     *
     * @param id Número correlativo de la revista.
     * @return true si la eliminación fue exitosa, false si ocurrió un error.
     */
    @Override
    public boolean eliminar(int id) {
        String deleteSQL = "DELETE FROM Material WHERE id_material = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(deleteSQL)) {

            stmt.setString(1, String.format("REV%05d", id));
            int filasEliminadas = stmt.executeUpdate();
            return filasEliminadas > 0;

        } catch (SQLException e) {
            logger.error("Error al eliminar la revista: " + e.getMessage(), e);
            return false;
        }
    }

    /**
     * Busca una revista en la base de datos usando su identificador único.
     * Se realiza un JOIN entre las tablas 'Material' y 'Revista'.
     *
     * @param id Identificador completo del material (ej: "REV00042").
     * @return Objeto Revista si se encuentra, o null si no existe.
     */
    @Override
    public Revista buscarPorID(String id) {
        String sql = "SELECT m.id_material, m.titulo, m.unidades_disponibles, " +
                "r.editorial, r.periodicidad, r.fecha_publicacion " +
                "FROM Material m " +
                "JOIN Revista r ON m.id_material = r.id_material " +
                "WHERE m.id_material = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Revista revista = new Revista();
                revista.setCodigoIdentificacion(rs.getString("id_material"));
                revista.setTitulo(rs.getString("titulo"));
                revista.setUnidadesDisponibles(rs.getInt("unidades_disponibles"));
                revista.setEditorial(rs.getString("editorial"));
                revista.setPeriodicidad(rs.getString("periodicidad"));
                revista.setFechaPublicacion(rs.getString("fecha_publicacion"));
                return revista;
            }

        } catch (SQLException e) {
            logger.error("Error al buscar la revista con ID " + id + ": " + e.getMessage(), e);
        }

        return null;
    }

    /**
     * Recupera todas las revistas registradas en la base de datos.
     * Se combinan datos de 'Material' y 'Revista'.
     *
     * @return Lista de objetos Revista. Devuelve una lista vacía si no hay registros.
     */
    @Override
    public List<Revista> listarTodos() {
        List<Revista> revistas = new ArrayList<>();

        String sql = "SELECT m.id_material, m.titulo, m.unidades_disponibles, " +
                "r.editorial, r.periodicidad, r.fecha_publicacion " +
                "FROM Material m " +
                "JOIN Revista r ON m.id_material = r.id_material";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Revista revista = new Revista();
                revista.setCodigoIdentificacion(rs.getString("id_material"));
                revista.setTitulo(rs.getString("titulo"));
                revista.setUnidadesDisponibles(rs.getInt("unidades_disponibles"));
                revista.setEditorial(rs.getString("editorial"));
                revista.setPeriodicidad(rs.getString("periodicidad"));
                revista.setFechaPublicacion(rs.getString("fecha_publicacion"));
                revistas.add(revista);
            }

        } catch (SQLException e) {
            logger.error("Error al listar las revistas: " + e.getMessage(), e);
        }

        return revistas;
    }
}
