package controllers;

import dao.LoginDAO;
import model.Usuario;

public class LoginController {

    private final LoginDAO loginDAO = new LoginDAO();

    public Usuario validar(String usuarioInput, String contrasenaInput) {
        Usuario usuario = loginDAO.buscarPorUsuario(usuarioInput);

        if (usuario == null){
            return null;
        }

        if (!loginDAO.validarCredenciales(contrasenaInput, usuario)) {
            return null;
        }

        return usuario; // devolvés el usuario al servlet
    }
}
