package controllers;


import dao.RevistaDAO;
import model.Revista;

import java.util.List;

public class RevistaController  {
    private RevistaDAO RevistaDao = new RevistaDAO();

    public boolean agregarRevista(Revista revista) {
        return RevistaDao.agregar(revista);
    }

    public boolean actualizarRevista(Revista revista) {
        return RevistaDao.actualizar(revista);
    }

    public boolean eliminarRevista(int id) {
        return RevistaDao.eliminar(id);
    }

    public Revista buscarRevistaPorID(String id) {
        return RevistaDao.buscarPorID(id);
    }

    public List<Revista> listarTodosLasRevistas() {
        return RevistaDao.listarTodos();
    }

}