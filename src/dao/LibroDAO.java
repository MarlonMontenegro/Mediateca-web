package dao;

import model.Libro;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LibroDAO implements MaterialDAO<Libro> {

    private static final Logger logger = LogManager.getLogger(LibroDAO.class);

    /**
     * Inserta un nuevo registro de libro en la base de datos.
     * El proceso consta de dos pasos: primero se registra el material
     * en la tabla general 'Material' y luego se agregan los detalles específicos
     * del libro en la tabla 'Libro'.
     *
     * @param libro Objeto con los datos del material.
     * @return true si el registro se completó correctamente, false en caso de error.
     */
    @Override
    public boolean agregar(Libro libro) {

        String insertMaterial = "INSERT INTO Material (id_material, titulo, unidades_disponibles, id_tipo) VALUES (?, ?, ?, ?)";
        String insertLibro = "INSERT INTO Libro (id_material, autor, numero_paginas, editorial, isbn, anio_publicacion) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (
                    PreparedStatement stmtMaterial = conn.prepareStatement(insertMaterial);
                    PreparedStatement stmtLibro = conn.prepareStatement(insertLibro)
            ) {
                // Insertar datos para Material
                stmtMaterial.setString(1, libro.getCodigoIdentificacion());
                stmtMaterial.setString(2, libro.getTitulo());
                stmtMaterial.setInt(3, libro.getUnidadesDisponibles());
                stmtMaterial.setInt(4, 1);

                stmtMaterial.executeUpdate();

                // Insertar datos para Libro
                stmtLibro.setString(1, libro.getCodigoIdentificacion());
                stmtLibro.setString(2, libro.getAutor());
                stmtLibro.setInt(3, libro.getNumeroPaginas());
                stmtLibro.setString(4, libro.getEditorial());
                stmtLibro.setString(5, libro.getIsbn());
                stmtLibro.setString(6, libro.getAnioPublicacion());

                stmtLibro.executeUpdate();

                conn.commit();
                return true;

            } catch (SQLException e) {
                conn.rollback();
                logger.error("Error al insertar libro: " + e.getMessage(), e);
                return false;
            }

        } catch (SQLException e) {
            logger.error("Error de conexión al insertar libro: " + e.getMessage(), e);
            return false;
        }
    }

    /**
     * Actualiza los datos de un libro existente en la base de datos.
     * El proceso actualiza tanto la información general del material
     * como los detalles específicos del libro.
     *
     * @param libro Objeto con los datos actualizados del libro.
     * @return true si el registro se actualizó correctamente, false si hubo un error.
     *
     *  */
    @Override
    public boolean actualizar(Libro libro) {

        String updateMaterial = "UPDATE Material SET titulo = ?, unidades_disponibles = ? WHERE id_material = ?";
        String updateLibro = "UPDATE Libro SET autor = ?, numero_paginas = ?, editorial = ?, isbn = ?, anio_publicacion = ? WHERE id_material = ?";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (
                    PreparedStatement stmtMaterial = conn.prepareStatement(updateMaterial);
                    PreparedStatement stmtLibro = conn.prepareStatement(updateLibro)
            ) {
                // Actualizar datos en Material
                stmtMaterial.setString(1, libro.getTitulo());
                stmtMaterial.setInt(2, libro.getUnidadesDisponibles());
                stmtMaterial.setString(3, libro.getCodigoIdentificacion());
                stmtMaterial.executeUpdate();

                // Actualizar datos en Libro
                stmtLibro.setString(1, libro.getAutor());
                stmtLibro.setInt(2, libro.getNumeroPaginas());
                stmtLibro.setString(3, libro.getEditorial());
                stmtLibro.setString(4, libro.getIsbn());
                stmtLibro.setString(5, libro.getAnioPublicacion());
                stmtLibro.setString(6, libro.getCodigoIdentificacion());
                stmtLibro.executeUpdate();

                conn.commit();
                return true;

            } catch (SQLException e) {
                conn.rollback();
                logger.error("Error al actualizar el libro: " + e.getMessage(), e);
                return false;
            }

        } catch (SQLException e) {
            logger.error("Error de conexión al actualizar libro: " + e.getMessage(), e);
            return false;
        }
    }

    /**
     * Elimina un libro de la base de datos usando su ID.
     * Al eliminar el registro de la tabla 'Material', la fila asociada en
     * 'Libro' se elimina automáticamente gracias a la cláusula ON DELETE CASCADE.
     *
     * @param id Número correlativo del código de identificación del libro.
     * @return true si el registro fue eliminado, false en caso de error o si no se encontró.
     */
    @Override
    public boolean eliminar(int id) {
        String deleteSQL = "DELETE FROM Material WHERE id_material = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(deleteSQL)) {

            stmt.setString(1, String.format("LIB%05d", id));
            int filasEliminadas = stmt.executeUpdate();
            return filasEliminadas > 0;

        } catch (SQLException e) {
            logger.error("Error al eliminar el libro: " + e.getMessage(), e);
            return false;
        }
    }

    /**
     * Busca un libro en la base de datos según su ID.
     * Recupera información de las tablas 'Material' y 'Libro'.
     *
     * @param id Identificador completo del libro.
     * @return Objeto Libro si se encuentra, o null si no existe o ocurre un error.
     */
    @Override
    public Libro buscarPorID(String id) {
        String sql = "SELECT m.id_material, m.titulo, m.unidades_disponibles, " +
                "l.autor, l.numero_paginas, l.editorial, l.isbn, l.anio_publicacion " +
                "FROM Material m " +
                "JOIN Libro l ON m.id_material = l.id_material " +
                "WHERE m.id_material = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            var rs = stmt.executeQuery();

            if (rs.next()) {
                Libro libro = new Libro();
                libro.setCodigoIdentificacion(rs.getString("id_material"));
                libro.setTitulo(rs.getString("titulo"));
                libro.setUnidadesDisponibles(rs.getInt("unidades_disponibles"));
                libro.setAutor(rs.getString("autor"));
                libro.setNumeroPaginas(rs.getInt("numero_paginas"));
                libro.setEditorial(rs.getString("editorial"));
                libro.setIsbn(rs.getString("isbn"));
                libro.setAnioPublicacion(rs.getString("anio_publicacion"));
                return libro;
            }

        } catch (SQLException e) {
            logger.error("Error al buscar el libro con ID " + id + ": " + e.getMessage(), e);
        }

        return null;
    }

    /**
     * Recupera todos los libros registrados en la base de datos.
     * La consulta combina datos de las tablas 'Material' y 'Libro'
     * para devolver objetos completos.
     *
     * @return Lista de objetos Libro. Si no hay registros, se devuelve una lista vacía.
     */
    @Override
    public List<Libro> listarTodos() {
        List<Libro> libros = new ArrayList<>();

        String sql = "SELECT m.id_material, m.titulo, m.unidades_disponibles, " +
                "l.autor, l.numero_paginas, l.editorial, l.isbn, l.anio_publicacion " +
                "FROM Material m " +
                "JOIN Libro l ON m.id_material = l.id_material";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Libro libro = new Libro();
                libro.setCodigoIdentificacion(rs.getString("id_material"));
                libro.setTitulo(rs.getString("titulo"));
                libro.setUnidadesDisponibles(rs.getInt("unidades_disponibles"));
                libro.setAutor(rs.getString("autor"));
                libro.setNumeroPaginas(rs.getInt("numero_paginas"));
                libro.setEditorial(rs.getString("editorial"));
                libro.setIsbn(rs.getString("isbn"));
                libro.setAnioPublicacion(rs.getString("anio_publicacion"));

                libros.add(libro);
            }

        } catch (SQLException e) {
            logger.error("Error al listar los libros: " + e.getMessage(), e);
        }

        return libros;
    }
}
