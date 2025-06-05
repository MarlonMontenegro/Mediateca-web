package model;

public class MaterialResumen extends Material {

    private String tipo;

    public MaterialResumen() {

    }

    public MaterialResumen(String codigoIdentificacion, String titulo, int unidadesDisponibles, String tipo) {
        super(codigoIdentificacion, titulo, unidadesDisponibles);
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String generarCodigo() {
        return codigoIdentificacion; // no genera nada nuevo
    }
}
