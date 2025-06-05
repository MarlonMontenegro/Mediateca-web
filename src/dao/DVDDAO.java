package dao;

import model.DVD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import util.DBConnection;

public class DVDDAO implements MaterialDAO<DVD> {

    private static final Logger logger = LogManager.getLogger(DVDDAO.class);

    @Override
    public boolean agregar(DVD dvd) {
        String insertMaterial = "INSERT INTO Material (id_material, titulo, unidades_disponibles, id_tipo) VALUES (?, ?, ?, ?)";
        String insertDVD = "INSERT INTO DVD (id_material, director, genero, duracion) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmtMaterial = conn.prepareStatement(insertMaterial);
                 PreparedStatement stmtDVD = conn.prepareStatement(insertDVD)) {

                stmtMaterial.setString(1, dvd.getCodigoIdentificacion());
                stmtMaterial.setString(2, dvd.getTitulo());
                stmtMaterial.setInt(3, dvd.getUnidadesDisponibles());
                stmtMaterial.setInt(4, 4); //

                stmtMaterial.executeUpdate();

                stmtDVD.setString(1, dvd.getCodigoIdentificacion());
                stmtDVD.setString(2, dvd.getDirector());
                stmtDVD.setString(3, dvd.getGenero());
                stmtDVD.setInt(4, Integer.parseInt(dvd.getDuracion()));

                stmtDVD.executeUpdate();

                conn.commit();
                return true;

            } catch (SQLException e) {
                conn.rollback();
                logger.error("Error al insertar DVD: " + e.getMessage(), e);
                return false;
            }

        } catch (SQLException e) {
            logger.error("Error de conexión al insertar DVD: " + e.getMessage(), e);
            return false;
        }
    }

    @Override
    public boolean actualizar(DVD dvd) {
        String updateMaterial = "UPDATE Material SET titulo = ?, unidades_disponibles = ? WHERE id_material = ?";
        String updateDVD = "UPDATE DVD SET director = ?, genero = ?, duracion = ? WHERE id_material = ?";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmtMaterial = conn.prepareStatement(updateMaterial);
                 PreparedStatement stmtDVD = conn.prepareStatement(updateDVD)) {

                stmtMaterial.setString(1, dvd.getTitulo());
                stmtMaterial.setInt(2, dvd.getUnidadesDisponibles());
                stmtMaterial.setString(3, dvd.getCodigoIdentificacion());
                stmtMaterial.executeUpdate();

                stmtDVD.setString(1, dvd.getDirector());
                stmtDVD.setString(2, dvd.getGenero());
                stmtDVD.setInt(3, Integer.parseInt(dvd.getDuracion()));
                stmtDVD.setString(4, dvd.getCodigoIdentificacion());
                stmtDVD.executeUpdate();

                conn.commit();
                return true;

            } catch (SQLException e) {
                conn.rollback();
                logger.error("Error al actualizar DVD: " + e.getMessage(), e);
                return false;
            }

        } catch (SQLException e) {
            logger.error("Error de conexión al actualizar DVD: " + e.getMessage(), e);
            return false;
        }
    }

    @Override
    public boolean eliminar(int id) {
        String deleteSQL = "DELETE FROM Material WHERE id_material = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(deleteSQL)) {

            stmt.setString(1, String.format("DVD%05d", id));
            int filasEliminadas = stmt.executeUpdate();
            return filasEliminadas > 0;

        } catch (SQLException e) {
            logger.error("Error al eliminar el DVD: " + e.getMessage(), e);
            return false;
        }
    }

    @Override
    public DVD buscarPorID(String id) {
        String sql = "SELECT m.id_material, m.titulo, m.unidades_disponibles, " +
                "d.director, d.genero, d.duracion " +
                "FROM Material m " +
                "JOIN DVD d ON m.id_material = d.id_material " +
                "WHERE m.id_material = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                DVD dvd = new DVD();
                dvd.setCodigoIdentificacion(rs.getString("id_material"));
                dvd.setTitulo(rs.getString("titulo"));
                dvd.setUnidadesDisponibles(rs.getInt("unidades_disponibles"));
                dvd.setDirector(rs.getString("director"));
                dvd.setGenero(rs.getString("genero"));
                dvd.setDuracion(String.valueOf(rs.getInt("duracion")));
                return dvd;
            }

        } catch (SQLException e) {
            logger.error("Error al buscar el DVD con ID " + id + ": " + e.getMessage(), e);
        }

        return null;
    }

    @Override
    public List<DVD> listarTodos() {
        List<DVD> dvds = new ArrayList<>();

        String sql = "SELECT m.id_material, m.titulo, m.unidades_disponibles, " +
                "d.director, d.genero, d.duracion " +
                "FROM Material m " +
                "JOIN DVD d ON m.id_material = d.id_material";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                DVD dvd = new DVD();
                dvd.setCodigoIdentificacion(rs.getString("id_material"));
                dvd.setTitulo(rs.getString("titulo"));
                dvd.setUnidadesDisponibles(rs.getInt("unidades_disponibles"));
                dvd.setDirector(rs.getString("director"));
                dvd.setGenero(rs.getString("genero"));
                dvd.setDuracion(String.valueOf(rs.getInt("duracion")));
                dvds.add(dvd);
            }

        } catch (SQLException e) {
            logger.error("Error al listar los DVDs: " + e.getMessage(), e);
        }

        return dvds;
    }
}

