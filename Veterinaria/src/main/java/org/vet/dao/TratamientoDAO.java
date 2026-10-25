package org.vet.dao;

import org.vet.model.Tratamiento;
import org.vet.util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TratamientoDAO {

    public int insertarTratamiento(Tratamiento tratamiento) {
        String sql = "INSERT INTO Tratamientos (Fecha, Descripcion, Coste, ID_Mascota) VALUES (?, ?, ?, ?)";
        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setDate(1, (Date) tratamiento.getFecha());
            ps.setString(2, tratamiento.getDescripcion());
            ps.setDouble(3, tratamiento.getCoste());
            ps.setInt(4, tratamiento.getIdMascota());

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        int idGenerado = rs.getInt(1);
                        tratamiento.setId(idGenerado);
                        return idGenerado;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar tratamiento: " + e.getMessage());
        }
        return -1;
    }

    public List<Tratamiento> obtenerTratamientosPorMascota(int idMascota) {
        List<Tratamiento> lista = new ArrayList<>();
        String sql = "SELECT ID_Tratamiento, Fecha, Descripcion, Coste, ID_Mascota FROM Tratamientos WHERE ID_Mascota = ? ORDER BY Fecha DESC";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idMascota);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Tratamiento tratamiento = new Tratamiento(
                            rs.getDate("Fecha"),
                            rs.getString("Descripcion"),
                            rs.getDouble("Coste"),
                            rs.getInt("ID_Mascota")
                    );
                    tratamiento.setId(rs.getInt("ID_Tratamiento"));
                    lista.add(tratamiento);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener tratamientos de la mascota: " + e.getMessage());
        }
        return lista;
    }
}