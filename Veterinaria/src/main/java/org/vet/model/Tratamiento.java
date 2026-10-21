package org.vet.model;

import java.time.LocalDate;

public class Tratamiento {
    private int id;
    private LocalDate fecha;
    private String descripcion;
    private double coste;
    private int idMascota;


    public Tratamiento() {
    }


    public Tratamiento(LocalDate fecha, String descripcion, double coste, int idMascota) {
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.coste = coste;
        this.idMascota = idMascota;
    }


    public Tratamiento(int id, LocalDate fecha, String descripcion, double coste, int idMascota) {
        this.id = id;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.coste = coste;
        this.idMascota = idMascota;
    }


    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public double getCoste() { return coste; }
    public void setCoste(double coste) { this.coste = coste; }

    public int getIdMascota() { return idMascota; }
    public void setIdMascota(int idMascota) { this.idMascota = idMascota; }
}