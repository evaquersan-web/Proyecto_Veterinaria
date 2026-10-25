package org.vet.service;

import org.vet.model.Cliente;
import org.vet.model.Mascota;
import org.vet.model.Tratamiento;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FacturaService {

    public static File generarFacturaTxt(Tratamiento t, Mascota m, Cliente c) throws IOException {
        String nombreFichero = String.format("Factura_%d_%s.txt", t.getId(), c.getDni());
        File archivo = new File(nombreFichero);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
            writer.write("========================================\n");
            writer.write("          CLINICA VETERINARIA VETCARE   \n");
            writer.write("========================================\n");
            writer.write("Num. Factura: " + t.getId() + "\n");
            writer.write("Fecha:        " + t.getFecha() + "\n");
            writer.write("----------------------------------------\n");
            writer.write("DATOS DEL CLIENTE:\n");
            writer.write("Nombre:   " + c.getNombre() + "\n");
            writer.write("DNI:      " + c.getDni() + "\n");
            writer.write("Telefono: " + c.getTelefono() + "\n");
            writer.write("----------------------------------------\n");
            writer.write("DATOS DE LA MASCOTA:\n");
            writer.write("Nombre:  " + m.getNombre() + "\n");
            writer.write("Especie: " + m.getEspecie() + "\n");
            writer.write("Raza:    " + m.getRaza() + "\n");
            writer.write("----------------------------------------\n");
            writer.write("DETALLE DEL TRATAMIENTO:\n");
            writer.write("Descripcion: " + t.getDescripcion() + "\n");
            writer.write(String.format("Importe Total: %.2f EUR\n", t.getCoste()));
            writer.write("========================================\n");
        }

        return archivo;
    }
}