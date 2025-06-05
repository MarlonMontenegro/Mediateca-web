package model;


public abstract class Usuario {

    private int id;
    private String usuario;
    private String contrasena;
    private RolUsuario rol;
    private boolean activo; 

    public Usuario() {
    }

    public Usuario(String usuario, String contrasena, RolUsuario rol) {
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.rol = rol;
        this.activo = true; 
    }

    public Usuario(int id, String usuario, String contrasena, RolUsuario rol) {
        this.id = id;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.rol = rol;
        this.activo = true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public RolUsuario getRol() {
        return rol;
    }

    public void setRol(RolUsuario rol) {
        this.rol = rol;
    }
    
    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
    public abstract String redirect();
}
