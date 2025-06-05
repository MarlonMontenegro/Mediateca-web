package controllers;

import dao.MaterialResumenDAO;
import model.MaterialResumen;

import java.util.List;

public class MaterialResumenController {

    private final MaterialResumenDAO dao = new MaterialResumenDAO();

    /**
     * Retorna una lista con todos los materiales disponibles,
     * incluyendo libros, revistas, CDs y DVDs.
     */
    public List<MaterialResumen> listarTodosLosMateriales() {
        return dao.listarTodosLosMateriales();
    }
    
    public List<MaterialResumen> buscarMateriales(String termino, String tipo) {
    return dao.buscarMateriales(termino, tipo);
}
}
