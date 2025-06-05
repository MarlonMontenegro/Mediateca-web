package dao;

import model.Prestamo;
import util.DBConnection;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PrestamoDAO {

    private static final Logger logger = LogManager.getLogger(PrestamoDAO.class);

    public boolean registrarPrestamo(Prestamo prestamo) {

        // Validar que no tenga mora pendiente
        if (tieneMoraPendiente(prestamo.getIdUsuario())) {
            logger.warn("Usuario con ID {} tiene mora pendiente. No se permite préstamo.", prestamo.getIdUsuario());
            return false;
        }

        // Validar que no haya superado el máximo de préstamos activos
        if (superaLimitePrestamosActivos(prestamo.getIdUsuario())) {
            logger.warn("Usuario con ID {} ya alcanzó el límite de préstamos activos.", prestamo.getIdUsuario());
            return false;
        }

        String sql = "INSERT INTO prestamo (id_usuario, id_material, fecha_prestamo, fecha_devolucion_esperada, fecha_devolucion_real, dias_mora, mora_calculada, devuelto) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (prestamo.getFechaDevolucionEsperada() == null) {
                prestamo.calcularFechaDevolucionEsperada();
            }

            stmt.setInt(1, prestamo.getIdUsuario());
            stmt.setString(2, prestamo.getIdMaterial());
            stmt.setDate(3, new Date(prestamo.getFechaPrestamo().getTime()));
            stmt.setDate(4, new Date(prestamo.getFechaDevolucionEsperada().getTime()));

            if (prestamo.getFechaDevolucionReal() != null) {
                stmt.setDate(5, new Date(prestamo.getFechaDevolucionReal().getTime()));
            } else {
                stmt.setNull(5, Types.DATE);
            }

            stmt.setInt(6, prestamo.getDiasMora());
            stmt.setBigDecimal(7, prestamo.getMoraCalculada() != null ? prestamo.getMoraCalculada() : BigDecimal.ZERO);
            stmt.setBoolean(8, prestamo.isDevuelto());

            int filas = stmt.executeUpdate();
            logger.info("Préstamo registrado con éxito para usuario ID {}", prestamo.getIdUsuario());
            return filas > 0;

        } catch (SQLException e) {
            logger.error("Error al registrar préstamo: {}", e.getMessage(), e);
            return false;
        }
    }

    public boolean devolverPrestamo(int idPrestamo, java.util.Date fechaDevolucionReal) {

        String sql = "UPDATE prestamo SET fecha_devolucion_real = ?, devuelto = TRUE WHERE id_prestamo = ?";


        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Asignar la fecha real de devolución
            stmt.setDate(1, new java.sql.Date(fechaDevolucionReal.getTime()));

            // ID del préstamo que se va a actualizar
            stmt.setInt(2, idPrestamo);

            // Ejecutar el UPDATE
            int filas = stmt.executeUpdate();

            if (filas > 0) {
                logger.info("Devolución registrada para préstamo ID {}", idPrestamo);
                return true;
            } else {
                logger.warn("No se encontró el préstamo ID {} para devolver.", idPrestamo);
                return false;
            }

        } catch (SQLException e) {
            logger.error("Error al registrar devolución: {}", e.getMessage(), e);
            return false;
        }
    }

    public List<Prestamo> ListarPrestamos(int idPrestamo) {

        List<Prestamo> prestamos = new ArrayList<>();

        String sql = "SELECT * FROM prestamo";

        try (Connection conn = DBConnection.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Prestamo p = new Prestamo();

                p.setIdPrestamo(rs.getInt("id_prestamo"));
                p.setIdUsuario(rs.getInt("id_usuario"));
                p.setIdMaterial(rs.getString("id_material"));
                p.setFechaPrestamo(rs.getDate("fecha_prestamo"));
                p.setFechaDevolucionEsperada(rs.getDate("fecha_devolucion_esperada"));
                p.setFechaDevolucionReal(rs.getDate("fecha_devolucion_real"));
                p.setDiasMora(rs.getInt("dias_mora"));
                p.setMoraCalculada(rs.getBigDecimal("mora_calculada"));
                p.setDevuelto(rs.getBoolean("devuelto"));

                prestamos.add(p);

            }

            logger.info("Se listaron {} préstamos.", prestamos.size());

        } catch (SQLException e) {
            logger.error("Error al listar préstamos: {}", e.getMessage(), e);
        }

        return prestamos;
    }
    


    private boolean superaLimitePrestamosActivos(int idUsuario) {
        String sqlPrestamos = "SELECT COUNT(*) FROM prestamo WHERE id_usuario = ? AND devuelto = FALSE";
        String sqlConfig = "SELECT ejemplares_maximos FROM ConfiguracionSistema ORDER BY id DESC LIMIT 1";

        try (Connection conn = DBConnection.getConnection()) {

            // Leemos los máximos de ejemplares permitidos
            int limite = 3; // valor por defecto por si falla
            try (PreparedStatement stmtConfig = conn.prepareStatement(sqlConfig);
                 ResultSet rsConfig = stmtConfig.executeQuery()) {
                if (rsConfig.next()) {
                    limite = rsConfig.getInt("ejemplares_maximos");
                }
            }

            // Contamos préstamos activos del usuario
            try (PreparedStatement stmtPrestamos = conn.prepareStatement(sqlPrestamos)) {
                stmtPrestamos.setInt(1, idUsuario);
                ResultSet rs = stmtPrestamos.executeQuery();
                if (rs.next()) {
                    int prestamosActivos = rs.getInt(1);
                    return prestamosActivos >= limite;
                }
            }

        } catch (SQLException e) {
            logger.error("Error al verificar límite de préstamos: {}", e.getMessage(), e);
        }
        return false;
    }

    private boolean tieneMoraPendiente(int idUsuario) {
        String sql =  "SELECT COUNT(*) FROM prestamo WHERE id_usuario = ? AND devuelto = TRUE AND mora_calculada > 0";


        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int cantidad = rs.getInt(1);
                return cantidad > 0;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return false;
    }
    
public List<Prestamo> listarTodosLosPrestamosConMora() {
    List<Prestamo> prestamosConMora = new ArrayList<>();

    String sql = "SELECT * FROM prestamo WHERE mora_calculada > 0";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            Prestamo p = new Prestamo();
            p.setIdPrestamo(rs.getInt("id_prestamo"));
            p.setIdUsuario(rs.getInt("id_usuario"));
            p.setIdMaterial(rs.getString("id_material"));
            p.setFechaPrestamo(rs.getDate("fecha_prestamo"));
            p.setFechaDevolucionEsperada(rs.getDate("fecha_devolucion_esperada"));
            p.setFechaDevolucionReal(rs.getDate("fecha_devolucion_real"));
            p.setDiasMora(rs.getInt("dias_mora"));
            p.setMoraCalculada(rs.getBigDecimal("mora_calculada"));
            p.setDevuelto(rs.getBoolean("devuelto"));

            prestamosConMora.add(p);
        }

        logger.info("Se listaron {} préstamos con mora.", prestamosConMora.size());

    } catch (SQLException e) {
        logger.error("Error al listar préstamos con mora: {}", e.getMessage(), e);
    }

    return prestamosConMora;
}
}



