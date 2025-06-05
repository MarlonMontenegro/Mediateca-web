package dao;

import java.util.List;
/**
 * Interfaz genérica para operaciones CRUD sobre materiales en la mediateca.
 *
 * @param <T> Tipo de material que será gestionado (Libro, Revista, CD, DVD, etc.).
 */
public interface MaterialDAO<T> {

    /**
     * Agrega un nuevo material a la base de datos.
     *
     * @param material Objeto con los datos del material.
     * @return true si se agregó correctamente; false en caso de error.
     */
    boolean agregar(T material);

    /**
     * Agrega un nuevo material a la base de datos.
     *
     * @param material Objeto con los datos del material.
     * @return true si se agregó correctamente; false en caso de error.
     */
    boolean actualizar(T material);

    /**
     * Elimina un material por su ID.
     *
     * @param id Identificador único del material.
     * @return true si se eliminó correctamente; false si no se encontró o falló.
     */
    boolean eliminar(int id);

    /**
     * Busca un material por su ID.
     *
     * @param id Identificador único del material.
     * @return El objeto encontrado, o null si no existe.
     */
    T buscarPorID(String id);

    /**
     * Obtiene una lista con todos los materiales del tipo correspondiente.
     *
     * @return Lista de materiales.
     */
    List<T> listarTodos();

}