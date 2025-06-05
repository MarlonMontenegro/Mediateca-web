package model;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

public class Prestamo {

    private int idPrestamo;
    private int idUsuario;
    private String idMaterial;

    private Date fechaPrestamo;
    private Date fechaDevolucionEsperada;
    private Date fechaDevolucionReal;

    private int diasMora;
    private BigDecimal moraCalculada;

    private boolean devuelto;

    private final int DIAS_PRESTAMO = 6;

    public Prestamo() {
    }

    public Prestamo(int idPrestamo, int idUsuario, String idMaterial, Date fechaPrestamo, Date fechaDevolucionReal) {
        this.idPrestamo = idPrestamo;
        this.idUsuario = idUsuario;
        this.idMaterial = idMaterial;
        this.fechaPrestamo = fechaPrestamo;
        calcularFechaDevolucionEsperada();
        this.fechaDevolucionReal = fechaDevolucionReal;
    }

    public int getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(int idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(String idMaterial) {
        this.idMaterial = idMaterial;
    }

    public Date getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(Date fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public Date getFechaDevolucionEsperada() {
        return fechaDevolucionEsperada;
    }

    public void setFechaDevolucionEsperada(Date fechaDevolucionEsperada) {
        this.fechaDevolucionEsperada = fechaDevolucionEsperada;
    }

    public Date getFechaDevolucionReal() {
        return fechaDevolucionReal;
    }

    public void setFechaDevolucionReal(Date fechaDevolucionReal) {
        this.fechaDevolucionReal = fechaDevolucionReal;
    }

    public int getDiasMora() {
        return diasMora;
    }

    public void setDiasMora(int diasMora) {
        this.diasMora = diasMora;
    }

    public BigDecimal getMoraCalculada() {
        return moraCalculada;
    }

    public void setMoraCalculada(BigDecimal moraCalculada) {
        this.moraCalculada = moraCalculada;
    }

    public boolean isDevuelto() {
        return devuelto;
    }

    public void setDevuelto(boolean devuelto) {
        this.devuelto = devuelto;
    }

    // Método que calcula la fecha esperada automáticamente
    public void calcularFechaDevolucionEsperada() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaPrestamo);
        calendar.add(Calendar.DAY_OF_MONTH, DIAS_PRESTAMO);
        this.fechaDevolucionEsperada = calendar.getTime();
    }
}