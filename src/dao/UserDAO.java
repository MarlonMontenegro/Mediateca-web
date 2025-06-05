package dao;

import model.Usuario;

import java.util.List;

public interface UserDAO {

    boolean crearUsuario(Usuario usuario);
    Usuario obtenerPorId(int id);
    List<Usuario> listarTodos();
    boolean cambiarContrasena(int id, String nuevaClave);
    boolean desactivarUsuario(int id);


}
