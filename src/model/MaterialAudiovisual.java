package model;

public abstract class MaterialAudiovisual extends Material {
    protected String genero;
    protected String duracion;

    public MaterialAudiovisual() {
    }


    public MaterialAudiovisual(String codigoIdentificacion, String titulo, int unidadesDisponibles, String genero, String duracion) {
        super(codigoIdentificacion, titulo, unidadesDisponibles);
        this.genero = genero;
        this.duracion = duracion;
    }

    public String getGenero() {
        return genero;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }
}
