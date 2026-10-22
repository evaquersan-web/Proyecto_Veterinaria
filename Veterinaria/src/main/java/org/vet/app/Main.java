
package org.vet.app;

import org.vet.util.ConexionBD;
import org.vet.dao.ClienteDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {


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
        ClienteDAO clienteDAO = new ClienteDAO();

        System.out.println("\n=== VETCARE ===");
        System.out.println("Registro de cliente");

        System.out.print("DNI: ");
        String dni = sc.nextLine();

        System.out.print("Nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Telefono: ");
        String telefono = sc.nextLine();

        System.out.print("Email: ");
        String email = sc.nextLine();

        boolean insertado = clienteDAO.insertarCliente(
                dni, nombre, telefono, email
        );

        if (insertado) {
            System.out.println("Cliente registrado correctamente.");
        } else {
            System.out.println("No se ha podido registrar el cliente.");
        }

        sc.close();
    }
}
