package controllers;

import model.Libro;
import dao.LibroDAO;
import java.util.List;

public class LibroController {
    
    private LibroDAO libroDao = new LibroDAO();

    public boolean agregarLibro(Libro libro) {
        return libroDao.agregar(libro);
    }

    public boolean actualizarLibro(Libro libro) {
        return libroDao.actualizar(libro);
    }

    public boolean eliminarLibro(int id) {
        return libroDao.eliminar(id);
    }

    public Libro buscarLibroPorID(String id) {
        return libroDao.buscarPorID(id);
    }

    public List<Libro> listarTodosLoslibros() {
        return libroDao.listarTodos();
    }
}