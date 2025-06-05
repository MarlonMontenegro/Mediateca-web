package model;

public class Revista extends MaterialEscrito {

    private String periodicidad;
    private String fechaPublicacion;

    public Revista() {}


    public Revista(String codigoIdentificacion, String titulo, int unidadesDisponibles, String editorial, String periodicidad, String fechaPublicacion) {
        super(codigoIdentificacion, titulo, unidadesDisponibles, editorial);
        this.periodicidad = periodicidad;
        this.fechaPublicacion = fechaPublicacion;
    }

    public String getPeriodicidad() {
        return periodicidad;
    }

    public void setPeriodicidad(String periodicidad) {
        this.periodicidad = periodicidad;
    }

    public String getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(String fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    @Override
    public String generarCodigo() {
        return "REV" + String.format("%05d", (int) (Math.random() * 100000));
    }
}
