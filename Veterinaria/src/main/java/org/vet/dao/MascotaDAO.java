package org.vet.dao;

import org.vet.model.Cliente;
import org.vet.model.Mascota;
import org.vet.util.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MascotaDAO {
    public boolean insertarMascota(Mascota mascota) {
        String sql = "INSERT INTO mascotas ( nombre, Especie, Raza , FechaDeNacimiento,ID_Cliente) VALUES ( ?, ?, ?, ?, ?)";


        try (Connection conn = ConexionBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {


            stmt.setString(1, mascota.getNombre());
            stmt.setString(2, mascota.getEspecie());
            stmt.setString(3, mascota.getRaza());
            stmt.setDate(4, mascota.getFechaNacimiento());
            stmt.setInt(5,mascota.getIdCliente());

            stmt.executeUpdate();
            System.out.println(" Mascota insertada correctamente.");
            return true;

        } catch (SQLException e) {
            System.err.println(" Error al insertar la mascota: " + e.getMessage());
            return false;
        }
    }
}
