package org.vet.dao;

import org.vet.model.Mascota;
import org.vet.util.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MascotaDAO {

    public int insertarMascota(Mascota mascota) {
        String sql = "INSERT INTO Mascotas (Nombre, Especie, Raza, FechaNacimiento, ID_Cliente) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, mascota.getNombre());
            ps.setString(2, mascota.getEspecie());
            ps.setString(3, mascota.getRaza());
            ps.setDate(4, mascota.getFechaNacimiento());
            ps.setInt(5, mascota.getIdCliente());

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        int idGenerado = rs.getInt(1);
                        mascota.setId(idGenerado);
                        return idGenerado;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar mascota: " + e.getMessage());
        }
        return -1;
    }

    public Mascota obtenerMascotaPorId(int idMascota) {
        String sql = "SELECT ID_Mascota, Nombre, Especie, Raza, FechaNacimiento, ID_Cliente FROM Mascotas WHERE ID_Mascota = ?";
        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idMascota);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Mascota mascota = new Mascota(
                            rs.getString("Nombre"),
                            rs.getString("Especie"),
                            rs.getString("Raza"),
                            rs.getDate("FechaNacimiento"),
                            rs.getInt("ID_Cliente")
                    );
                    mascota.setId(rs.getInt("ID_Mascota"));
                    return mascota;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener mascota por ID: " + e.getMessage());
        }
        return null;
    }
}