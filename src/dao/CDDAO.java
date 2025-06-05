package dao;

import model.CDdeAudio;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class CDDAO implements MaterialDAO<CDdeAudio> {

    private static final Logger logger = LogManager.getLogger(CDDAO.class);




    /**
     * Inserta un nuevo CD de audio en la base de datos.
     * Primero registra el material general y luego los datos específicos del CD.
     *
     * @param cd Objeto CDdeAudio con los datos a registrar.
     * @return true si el registro fue exitoso, false si ocurrió algún error.
     */
    @Override
    public boolean agregar(CDdeAudio cd) {
        String insertMaterial = "INSERT INTO Material (id_material, titulo, unidades_disponibles, id_tipo) VALUES (?, ?, ?, ?)";
        String insertCD = "INSERT INTO CD_Audio (id_material, artista, genero, duracion, numero_canciones) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmtMaterial = conn.prepareStatement(insertMaterial);
                 PreparedStatement stmtCD = conn.prepareStatement(insertCD)) {

                // Insertar en Material
                stmtMaterial.setString(1, cd.getCodigoIdentificacion());
                stmtMaterial.setString(2, cd.getTitulo());
                stmtMaterial.setInt(3, cd.getUnidadesDisponibles());
                stmtMaterial.setInt(4, 3); // 3 representa el tipo CD_Audio en TipoMaterial

                stmtMaterial.executeUpdate();

                // Insertar en CD_Audio
                stmtCD.setString(1, cd.getCodigoIdentificacion());
                stmtCD.setString(2, cd.getArtista());
                stmtCD.setString(3, cd.getGenero());
                stmtCD.setInt(4, Integer.parseInt(cd.getDuracion()));
                stmtCD.setInt(5, cd.getNumeroCanciones());

                stmtCD.executeUpdate();

                conn.commit();
                return true;

            } catch (SQLException e) {
                conn.rollback();
                logger.error("Error al insertar CD de audio: " + e.getMessage(), e);
                return false;
            }

        } catch (SQLException e) {
            logger.error("Error de conexión al insertar CD de audio: " + e.getMessage(), e);
            return false;
        }
    }

    /**
     * Actualiza un CD de audio existente en la base de datos.
     *
     * @param cd Objeto CDdeAudio con los datos actualizados.
     * @return true si la actualización fue exitosa, false si ocurrió algún error.
     */
    @Override
    public boolean actualizar(CDdeAudio cd) {
        String updateMaterial = "UPDATE Material SET titulo = ?, unidades_disponibles = ? WHERE id_material = ?";
        String updateCD = "UPDATE CD_Audio SET artista = ?, genero = ?, duracion = ?, numero_canciones = ? WHERE id_material = ?";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmtMaterial = conn.prepareStatement(updateMaterial);
                 PreparedStatement stmtCD = conn.prepareStatement(updateCD)) {

                // Actualizar Material
                stmtMaterial.setString(1, cd.getTitulo());
                stmtMaterial.setInt(2, cd.getUnidadesDisponibles());
                stmtMaterial.setString(3, cd.getCodigoIdentificacion());
                stmtMaterial.executeUpdate();

                // Actualizar CD_Audio
                stmtCD.setString(1, cd.getArtista());
                stmtCD.setString(2, cd.getGenero());
                stmtCD.setInt(3, Integer.parseInt(cd.getDuracion()));
                stmtCD.setInt(4, cd.getNumeroCanciones());
                stmtCD.setString(5, cd.getCodigoIdentificacion());
                stmtCD.executeUpdate();

                conn.commit();
                return true;

            } catch (SQLException e) {
                conn.rollback();
                logger.error("Error al actualizar CD de audio: " + e.getMessage(), e);
                return false;
            }

        } catch (SQLException e) {
            logger.error("Error de conexión al actualizar CD de audio: " + e.getMessage(), e);
            return false;
        }
    }

    /**
     * Elimina un CD de audio de la base de datos usando su ID.
     *
     * @param id Número correlativo del CD (ej: 42 para CDA00042).
     * @return true si la eliminación fue exitosa, false si ocurrió algún error.
     */
    @Override
    public boolean eliminar(int id) {
        String deleteSQL = "DELETE FROM Material WHERE id_material = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(deleteSQL)) {

            stmt.setString(1, String.format("CDA%05d", id));
            int filasEliminadas = stmt.executeUpdate();
            return filasEliminadas > 0;

        } catch (SQLException e) {
            logger.error("Error al eliminar el CD de audio: " + e.getMessage(), e);
            return false;
        }
    }

    /**
     * Busca un CD de audio específico en la base de datos.
     *
     * @param id Identificador completo del CD.
     * @return Objeto CDdeAudio si se encuentra, o null si no existe.
     */
    @Override
    public CDdeAudio buscarPorID(String id) {
        String sql = "SELECT m.id_material, m.titulo, m.unidades_disponibles, " +
                "c.artista, c.genero, c.duracion, c.numero_canciones " +
                "FROM Material m " +
                "JOIN CD_Audio c ON m.id_material = c.id_material " +
                "WHERE m.id_material = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                CDdeAudio cd = new CDdeAudio();
                cd.setCodigoIdentificacion(rs.getString("id_material"));
                cd.setTitulo(rs.getString("titulo"));
                cd.setUnidadesDisponibles(rs.getInt("unidades_disponibles"));
                cd.setArtista(rs.getString("artista"));
                cd.setGenero(rs.getString("genero"));
                cd.setDuracion(String.valueOf(rs.getInt("duracion")));
                cd.setNumeroCanciones(rs.getInt("numero_canciones"));
                return cd;
            }

        } catch (SQLException e) {
            logger.error("Error al buscar el CD de audio con ID " + id + ": " + e.getMessage(), e);
        }

        return null;
    }

    /**
     * Recupera todos los CDs de audio registrados en la base de datos.
     *
     * @return Lista de objetos CDdeAudio. Devuelve una lista vacía si no hay registros.
     */
    @Override
    public List<CDdeAudio> listarTodos() {
        List<CDdeAudio> cds = new ArrayList<>();

        String sql = "SELECT m.id_material, m.titulo, m.unidades_disponibles, " +
                "c.artista, c.genero, c.duracion, c.numero_canciones " +
                "FROM Material m " +
                "JOIN CD_Audio c ON m.id_material = c.id_material";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                CDdeAudio cd = new CDdeAudio();
                cd.setCodigoIdentificacion(rs.getString("id_material"));
                cd.setTitulo(rs.getString("titulo"));
                cd.setUnidadesDisponibles(rs.getInt("unidades_disponibles"));
                cd.setArtista(rs.getString("artista"));
                cd.setGenero(rs.getString("genero"));
                cd.setDuracion(String.valueOf(rs.getInt("duracion")));
                cd.setNumeroCanciones(rs.getInt("numero_canciones"));
                cds.add(cd);
            }

        } catch (SQLException e) {
            logger.error("Error al listar los CDs de audio: " + e.getMessage(), e);
        }

        return cds;
    }
}
