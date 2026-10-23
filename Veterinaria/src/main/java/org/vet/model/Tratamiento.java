package org.vet.model;

import java.time.LocalDate;
import java.util.Date;

public class Tratamiento {
    private int id;
    private Date fecha;
    private String descripcion;
    private double coste;
    private int idMascota;


    public Tratamiento() {
    }


    public Tratamiento(Date fecha, String descripcion, double coste, int idMascota) {
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.coste = coste;
        this.idMascota = idMascota;
    }


    public Tratamiento(int id, Date fecha, String descripcion, double coste, int idMascota) {
        this.id = id;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.coste = coste;
        this.idMascota = idMascota;
    }


    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public double getCoste() { return coste; }
    public void setCoste(double coste) { this.coste = coste; }

    public int getIdMascota() { return idMascota; }
    public void setIdMascota(int idMascota) { this.idMascota = idMascota; }
}