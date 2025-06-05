package model;

public abstract class Material {
    protected String codigoIdentificacion;
    protected String titulo;
    protected int unidadesDisponibles;

    public Material() {
    }


    public Material(String codigoIdentificacion, String titulo, int unidadesDisponibles) {
        this.codigoIdentificacion = codigoIdentificacion;
        this.titulo = titulo;
        this.unidadesDisponibles = unidadesDisponibles;
    }


    public String getCodigoIdentificacion() {
        return codigoIdentificacion;
    }

    public void setCodigoIdentificacion(String codigoIdentificacion) {
        this.codigoIdentificacion = codigoIdentificacion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getUnidadesDisponibles() {
        return unidadesDisponibles;
    }

    public void setUnidadesDisponibles(int unidadesDisponibles) {
        this.unidadesDisponibles = unidadesDisponibles;
    }

    public abstract String generarCodigo();
}
