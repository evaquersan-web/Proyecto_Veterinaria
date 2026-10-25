package org.vet.app;

import org.vet.dao.ClienteDAO;
import org.vet.dao.MascotaDAO;
import org.vet.dao.TratamientoDAO;
import org.vet.model.Cliente;
import org.vet.model.Mascota;
import org.vet.model.Tratamiento;
import org.vet.util.ConexionBD;
import org.vet.service.FacturaService;
import org.vet.util.FTPManager;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try (Connection con = ConexionBD.conectar()) {
            System.out.println("Conexion con la base de datos realizada correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos.");
            e.printStackTrace();
            return;
        }

        Scanner sc = new Scanner(System.in);
        ClienteDAO clienteDAO = new ClienteDAO();
        MascotaDAO mascotaDAO = new MascotaDAO();
        TratamientoDAO tratamientoDAO = new TratamientoDAO();

        FTPManager ftpManager = new FTPManager("localhost", 21, "vetcare", "1234");

        int eleccion = 0;
        System.out.println("----------------- Bienvenido a VetCare -----------------");

        while (eleccion != 5) {
            System.out.println("\n¿Que desea hacer?");
            System.out.println("1. Registrar un cliente.");
            System.out.println("2. Registrar una mascota.");
            System.out.println("3. Registrar un tratamiento a una mascota.");
            System.out.println("4. Consultar historial de tratamientos por mascota.");
            System.out.println("5. Cerrar VetCare");
            System.out.print("Opcion: ");

            if (!sc.hasNextInt()) {
                sc.next();
                System.out.println("Por favor, introduce un numero valido.");
                continue;
            }

            eleccion = sc.nextInt();
            sc.nextLine();

            if (eleccion == 1) {
                System.out.println("\n--- VETCARE ---");
                System.out.println("Registro de cliente");
                System.out.print("DNI: ");
                String dni = sc.nextLine();
                System.out.print("Nombre: ");
                String nombre = sc.nextLine();
                System.out.print("Telefono: ");
                String telefono = sc.nextLine();
                System.out.print("Email: ");
                String email = sc.nextLine();

                Cliente nuevoCliente = new Cliente(dni, nombre, telefono, email);
                clienteDAO.insertarCliente(nuevoCliente);
                System.out.println("Cliente registrado correctamente.");

            } else if (eleccion == 2) {
                System.out.println("\n--- VETCARE ---");
                System.out.println("Registro de mascota");
                System.out.print("Nombre: ");
                String nombre = sc.nextLine();
                System.out.print("Especie: ");
                String especie = sc.nextLine();
                System.out.print("Raza: ");
                String raza = sc.nextLine();
                System.out.print("Fecha de nacimiento (AAAA-MM-DD): ");
                String fechaStr = sc.nextLine();
                System.out.print("ID del cliente propietario: ");
                int idCliente = sc.nextInt();
                sc.nextLine();

                Mascota nuevaMascota = new Mascota(nombre, especie, raza, Date.valueOf(fechaStr), idCliente);
                mascotaDAO.insertarMascota(nuevaMascota);
                System.out.println("Mascota registrada correctamente.");

            } else if (eleccion == 3) {
                System.out.println("\n--- VETCARE ---");
                System.out.println("Registro de tratamiento");
                System.out.print("ID de la mascota: ");
                int idMascota = sc.nextInt();
                sc.nextLine();
                System.out.print("Descripcion del tratamiento: ");
                String descripcion = sc.nextLine();
                System.out.print("Coste: ");
                double coste = sc.nextDouble();
                sc.nextLine();

                Date fechaActual = new Date(System.currentTimeMillis());
                Tratamiento nuevoTratamiento = new Tratamiento(fechaActual, descripcion, coste, idMascota);

                int idTratamiento = tratamientoDAO.insertarTratamiento(nuevoTratamiento);
                nuevoTratamiento.setId(idTratamiento);
                System.out.println("Tratamiento registrado con ID: " + idTratamiento);

                Mascota mascota = mascotaDAO.obtenerMascotaPorId(idMascota);
                if (mascota != null) {
                    Cliente cliente = clienteDAO.obtenerClientePorId(mascota.getIdCliente());
                    if (cliente != null) {
                        try {
                            File facturaLocal = FacturaService.generarFacturaTxt(nuevoTratamiento, mascota, cliente);
                            System.out.println("\n[ACDA] Factura generada: " + facturaLocal.getName());

                            String carpetaRemota = "facturas";
                            boolean subidaCorrecta = ftpManager.subirFactura(facturaLocal, carpetaRemota);

                            if (subidaCorrecta) {
                                System.out.println("[PSEP] Factura subida al servidor FTP correctamente.");

                                File archivoDescargado = new File("descargada_" + facturaLocal.getName());
                                boolean descargaCorrecta = ftpManager.descargarFactura(facturaLocal.getName(), carpetaRemota, archivoDescargado);

                                if (descargaCorrecta) {
                                    System.out.println("[PSEP] Factura descargada del servidor para verificacion.");
                                    boolean integra = ftpManager.verificarIntegridad(facturaLocal, archivoDescargado);

                                    if (integra) {
                                        System.out.println("[PSEP] Verificacion de integridad: EXITOSA (tamaños y contenidos coinciden).");
                                    } else {
                                        System.out.println("[PSEP] Alerta: La factura descargada difiere del archivo original.");
                                    }
                                } else {
                                    System.out.println("[PSEP] Error al descargar la factura del FTP.");
                                }
                            } else {
                                System.out.println("[PSEP] Error al subir la factura al FTP.");
                            }
                        } catch (IOException e) {
                            System.out.println("Error en la gestion de ficheros de la factura: " + e.getMessage());
                        }
                    }
                }

            } else if (eleccion == 4) {
                System.out.println("\n--- VETCARE ---");
                System.out.print("Introduce el ID de la mascota a consultar: ");
                int idMascota = sc.nextInt();
                sc.nextLine();

                List<Tratamiento> tratamientos = tratamientoDAO.obtenerTratamientosPorMascota(idMascota);
                if (tratamientos == null || tratamientos.isEmpty()) {
                    System.out.println("No hay tratamientos registrados para la mascota con ID " + idMascota);
                } else {
                    System.out.println("\n--- Historial de Tratamientos (Mascota ID: " + idMascota + ") ---");
                    for (Tratamiento t : tratamientos) {
                        System.out.printf("ID: %d | Fecha: %s | Coste: %.2f EUR | Descripcion: %s%n",
                                t.getId(), t.getFecha(), t.getCoste(), t.getDescripcion());
                    }
                }

            } else if (eleccion == 5) {
                System.out.println("Cerrando la sesion de VetCare...");
            } else {
                System.out.println("Opcion no reconocida. Introduce un valor entre 1 y 5.");
            }
        }

        sc.close();
    }
}