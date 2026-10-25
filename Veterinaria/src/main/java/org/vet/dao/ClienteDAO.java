package org.vet.dao;

import org.vet.model.Cliente;
import org.vet.util.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ClienteDAO {

    public int insertarCliente(Cliente cliente) {
        String sql = "INSERT INTO Clientes (DNI, Nombre, Telefono, Email) VALUES (?, ?, ?, ?)";
        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, cliente.getDni());
            ps.setString(2, cliente.getNombre());
            ps.setString(3, cliente.getTelefono());
            ps.setString(4, cliente.getEmail());

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        int idGenerado = rs.getInt(1);
                        cliente.setId(idGenerado);
                        return idGenerado;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar cliente: " + e.getMessage());
        }
        return -1;
    }

    public Cliente obtenerClientePorId(int idCliente) {
        String sql = "SELECT ID_Cliente, DNI, Nombre, Telefono, Email FROM Clientes WHERE ID_Cliente = ?";
        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCliente);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Cliente cliente = new Cliente(
                            rs.getString("DNI"),
                            rs.getString("Nombre"),
                            rs.getString("Telefono"),
                            rs.getString("Email")
                    );
                    cliente.setId(rs.getInt("ID_Cliente"));
                    return cliente;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener cliente por ID: " + e.getMessage());
        }
        return null;
    }
}