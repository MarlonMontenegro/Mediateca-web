package model;

public class ConfiguracionSistema {
    private int id;
    private int ejemplaresMaximos;
    private double moraDiaria;

    public ConfiguracionSistema() {
    }

    public ConfiguracionSistema(int id, int ejemplaresMaximos, double moraDiaria) {
        this.id = id;
        this.ejemplaresMaximos = ejemplaresMaximos;
        this.moraDiaria = moraDiaria;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEjemplaresMaximos() {
        return ejemplaresMaximos;
    }

    public void setEjemplaresMaximos(int ejemplaresMaximos) {
        this.ejemplaresMaximos = ejemplaresMaximos;
    }

    public double getMoraDiaria() {
        return moraDiaria;
    }

    public void setMoraDiaria(double moraDiaria) {
        this.moraDiaria = moraDiaria;
    }

}
