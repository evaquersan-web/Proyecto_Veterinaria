package org.vet.dao;
import org.vet.model.Cliente;
import org.vet.util.ConexionBD;
import java.sql.*;

public class ClienteDAO {

    public boolean insertarCliente(Cliente cliente) {
        String sql = "INSERT INTO clientes (dni, nombre, telefono, email) VALUES (?, ?, ?, ?)";


        try (Connection conn = ConexionBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getDni());
            stmt.setString(2, cliente.getNombre());
            stmt.setString(3, cliente.getTelefono());
            stmt.setString(4, cliente.getEmail());

            stmt.executeUpdate();
            System.out.println(" Cliente insertado correctamente.");
            return true;

        } catch (SQLException e) {
            System.err.println(" Error al insertar el cliente: " + e.getMessage());
            return false;
        }
    }

    public boolean insertarCliente(String dni, String nombre,
                                   String telefono, String email) {

        Cliente cliente = new Cliente(dni,nombre,telefono,email);

        cliente.setDni(dni);
        cliente.setNombre(nombre);
        cliente.setTelefono(telefono);
        cliente.setEmail(email);

        return insertarCliente(cliente);
    }
}
