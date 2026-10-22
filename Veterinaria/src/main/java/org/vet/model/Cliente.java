package org.vet.model;

public class Cliente {

    private int id;
    private String dni;
    private String nombre;
    private String telefono;
    private String email;

    public Cliente(String dni, String nombre, String telefono, String email) {
        this.dni = dni;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getDni() { return dni; }
    public String getNombre() { return nombre; }
    public String getTelefono() { return telefono; }
    public String getEmail() { return email; }

    public void setDni(String dni) { }
    public void setNombre(String nombre) { }
    public void setTelefono(String telefono) { }
    public void setEmail(String email) { }
}
