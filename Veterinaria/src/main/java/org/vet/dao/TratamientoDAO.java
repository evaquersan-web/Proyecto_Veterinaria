package org.vet.dao;

import org.vet.model.Tratamiento;
import org.vet.util.ConexionBD;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TratamientoDAO {

    public int registrarTratamiento(Tratamiento tratamiento) {
        String sql = "INSERT INTO tratamientos (Fecha, Descripcion, Coste, ID_Mascota) VALUES (?, ?, ?, ?)";
        int idGenerado = -1;

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setDate(1, (Date) tratamiento.getFecha());
            stmt.setString(2, tratamiento.getDescripcion());
            stmt.setDouble(3, tratamiento.getCoste());
            stmt.setInt(4, tratamiento.getIdMascota());

            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        idGenerado = rs.getInt(1);
                        tratamiento.setId(idGenerado);
                        System.out.println("Tratamiento registrado correctamente con ID: " + idGenerado);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al registrar el tratamiento: " + e.getMessage());
        }
        return idGenerado;
    }


    public List<Tratamiento> obtenerTratamientosPorMascota(int idMascota) {
        List<Tratamiento> listaTratamientos = new ArrayList<>();
        String sql = "SELECT ID, Fecha, Descripcion, Coste, ID_Mascota FROM tratamientos WHERE ID_Mascota = ?";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idMascota);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("ID");
                    Date fecha = rs.getDate("Fecha");
                    String descripcion = rs.getString("Descripcion");
                    double coste = rs.getDouble("Coste");
                    int idMasco = rs.getInt("ID_Mascota");

                    Tratamiento tratamiento = new Tratamiento(id, fecha, descripcion, coste, idMasco);
                    listaTratamientos.add(tratamiento);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar el historial de tratamientos: " + e.getMessage());
        }

        return listaTratamientos;
    }
}
