package model;

public class CDdeAudio extends MaterialAudiovisual{
    private String artista;
    private int numeroCanciones;

    public CDdeAudio() {
    }

    public CDdeAudio(String codigoIdentificacion, String titulo, int unidadesDisponibles, String genero, String duracion, String artista, int numeroCanciones) {
        super(codigoIdentificacion, titulo, unidadesDisponibles, genero, duracion);
        this.artista = artista;
        this.numeroCanciones = numeroCanciones;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public int getNumeroCanciones() {
        return numeroCanciones;
    }

    public void setNumeroCanciones(int numeroCanciones) {
        this.numeroCanciones = numeroCanciones;
    }

    @Override
    public String generarCodigo() {
        return "CDA" + String.format("%05d", (int) (Math.random() * 100000));
    }
}
