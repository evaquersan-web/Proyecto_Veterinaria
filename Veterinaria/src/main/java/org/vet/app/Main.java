
package org.vet.app;

import org.vet.dao.MascotaDAO;
import org.vet.model.Mascota;
import org.vet.util.ConexionBD;
import org.vet.dao.ClienteDAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Formatter;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws ParseException {


        try (Connection conexion = ConexionBD.conectar()) {

            System.out.println(
                    "Conexion con la base de datos realizada correctamente."
            );

        } catch (SQLException e) {

            System.out.println("Error al conectar con la base de datos.");
            e.printStackTrace();
            return;
        }

        Scanner sc = new Scanner(System.in);
        int eleccion = 0;

        System.out.println("-------------Bienvenido a VetCare---------------");
        while (eleccion!=4) {
            System.out.println("¿Que desea hacer?");
            System.out.println("1. Registrar un cliente.");
            System.out.println("2. Registrar una mascota.");
            System.out.println("3. Registrar un tratamiento a una mascota.");
            System.out.println("4.Cerrar VetCare.");
            eleccion = sc.nextInt();

            if (eleccion == 1) {
                ClienteDAO clienteDAO = new ClienteDAO();

                System.out.println("\n=== VETCARE ===");
                System.out.println("Registro de cliente");

                System.out.print("DNI: ");
                String dni = sc.next();

                System.out.print("Nombre: ");
                String nombre = sc.next();

                System.out.print("Telefono: ");
                String telefono = sc.next();

                System.out.print("Email: ");
                String email = sc.next();

                clienteDAO.insertarCliente(dni, nombre, telefono, email);
            }

            if (eleccion ==2){
                MascotaDAO mascotaDAO = new MascotaDAO();

                System.out.println("Nombre: ");
                String nombre = sc.next();

                System.out.println("Especie: ");
                String especie = sc.next();

                System.out.println("Raza: ");
                String raza = sc.next();

                System.out.println("Fecha de nacimiento (formato DD/MM/YYYY): ");
                String fechaNacimiento = sc.next();
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                format.setLenient(false);
                Date fecha = (Date) format.parse(fechaNacimiento);

                System.out.println("Id del cliente: ");
                int nombreCliente = sc.nextInt();

                Mascota mascota = new Mascota(nombre, especie, raza, fecha, nombreCliente);

                mascotaDAO.insertarMascota(mascota);
            }

            if (eleccion == 4){
                break;
            }

            if (eleccion < 0 || eleccion > 4){
                System.out.println("Introduce un valor entre el 1 y el 4");
                eleccion=0;
            }
        }
        sc.close();
    }
}
