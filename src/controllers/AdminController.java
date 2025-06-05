package controllers;

import dao.AdminDAO;
import model.Usuario;

import java.util.List;

public class AdminController {

    private final AdminDAO adminDAO = new AdminDAO();

    public boolean crearUsuario(Usuario usuario) {
        return adminDAO.crearUsuario(usuario);
    }

    public Usuario obtenerUsuarioPorId(int id) {
        return adminDAO.obtenerPorId(id);
    }

    public List<Usuario> listarUsuarios() {
        return adminDAO.listarTodos();
    }

    public boolean cambiarContrasena(int id, String nuevaClave) {
        return adminDAO.cambiarContrasena(id, nuevaClave);
    }

    public boolean desactivarUsuario(int id) {
        return adminDAO.desactivarUsuario(id);
    }
}
