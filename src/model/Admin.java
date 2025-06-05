package model;

/**
 *
 * @author marlo
 */
public class Admin extends Usuario {

    /**
     *
     * @param id
     * @param usuario
     * @param contrasena
     * @param rol
     */
    public Admin(int id, String usuario, String contrasena, RolUsuario rol) {
        super(id, usuario, contrasena, rol);
    }

    public Admin(String usuario, String contrasena, RolUsuario rol) {
        super(usuario, contrasena, rol);
    }

    @Override
    public String redirect() {
            return "admin/dashboard";
    }
}
