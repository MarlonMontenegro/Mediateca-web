package model;

/**
 *
 * @author marlo
 */
public class Alumno extends Usuario {

    public Alumno(int id, String usuario, String contrasena, RolUsuario rol) {
        super(id, usuario, contrasena, rol);
    }

    public Alumno(String usuario, String contrasena, RolUsuario rol) {
        super(usuario, contrasena, rol);
    }

    @Override
    public String redirect() {
        return "alumno/dashboard";

    }
}
