package model;

public class DVD extends MaterialAudiovisual{
    private String director;

    public DVD() {
    }

    public DVD(String codigoIdentificacion, String titulo, int unidadesDisponibles, String genero, String duracion, String director) {
        super(codigoIdentificacion, titulo, unidadesDisponibles, genero, duracion);
        this.director = director;
    }

    public String getDirector() {


        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    @Override
    public String generarCodigo() {
        return "DVD" + String.format("%05d", (int) (Math.random() * 100000));
    }
}
