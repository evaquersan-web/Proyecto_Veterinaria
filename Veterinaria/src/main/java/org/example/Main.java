package org.example;

import java.sql.Connection;
import java.sql.SQLException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        try (Connection conexion = ConexionBD.conectar()) {

            System.out.println(
                    "Conexion con VetCare realizada correctamente."
            );

        } catch (SQLException e) {
            System.out.println(
                    "Error al conectar con la base de datos."
            );
            e.printStackTrace();
        }
    }
}