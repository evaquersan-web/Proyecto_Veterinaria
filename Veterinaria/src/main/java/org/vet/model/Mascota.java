package org.vet.model;

import java.time.LocalDate;

public class Mascota {
    private int id;
    private String nombre;
    private String especie;
    private String raza;
    private LocalDate fechaNacimiento;
    private int idCliente;

    public Mascota() {
    }

    public Mascota(String nombre, String especie, String raza, LocalDate fechaNacimiento, int idCliente) {
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.fechaNacimiento = fechaNacimiento;
        this.idCliente = idCliente;
    }

    public Mascota(int id, String nombre, String especie, String raza, LocalDate fechaNacimiento, int idCliente) {
        this.id = id;
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.fechaNacimiento = fechaNacimiento;
        this.idCliente = idCliente;
    }

        public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEspecie() { return especie; }
    public void setEspecie(String especie) { this.especie = especie; }

    public String getRaza() { return raza; }
    public void setRaza(String raza) { this.raza = raza; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }
}