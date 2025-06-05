package model;

public abstract class MaterialEscrito extends Material {
    protected String editorial;

    public MaterialEscrito() {
    }


    public MaterialEscrito(String codigoIdentificacion, String titulo, int unidadesDisponibles, String editorial) {
        super(codigoIdentificacion, titulo, unidadesDisponibles);
        this.editorial = editorial;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

}


