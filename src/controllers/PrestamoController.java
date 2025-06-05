package controllers;

import dao.PrestamoDAO;
import model.Prestamo;

import java.util.Date;
import java.util.List;

public class PrestamoController {

    private final PrestamoDAO prestamoDAO = new PrestamoDAO();

    /**
     * Registra un nuevo préstamo si el usuario no tiene mora y no ha superado el límite de préstamos activos.
     * @param prestamo Objeto Prestamo con los datos a registrar
     * @return true si se registró correctamente, false en caso contrario
     */
    public boolean crearPrestamo(Prestamo prestamo) {
        return prestamoDAO.registrarPrestamo(prestamo);
    }

    /**
     * Registra la devolución de un préstamo existente, lo marca como devuelto y deja que el trigger calcule mora.
     * @param idPrestamo ID del préstamo a devolver
     * @param fechaDevolucionReal Fecha real en que se devuelve el material
     * @return true si se actualizó correctamente, false si no se encontró o falló
     */
    public boolean devolverPrestamo(int idPrestamo, Date fechaDevolucionReal) {
        return prestamoDAO.devolverPrestamo(idPrestamo, fechaDevolucionReal);
    }

    /**
     * Lista todos los préstamos registrados en la base de datos.
     * @return Lista de objetos Prestamo
     */
    public List<Prestamo> listarPrestamos() {
        return prestamoDAO.ListarPrestamos(0); // el parámetro se ignora en tu implementación actual
    }
    
    public List<Prestamo> listarTodosLosPrestamosConMora() {
        return prestamoDAO.listarTodosLosPrestamosConMora();
    }
    
}