package org.vet;

import org.vet.util.ConexionBD;
import java.sql.Connection;
import java.sql.SQLException;

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