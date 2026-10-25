package org.vet.util;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FTPManager {

    private final String servidor;
    private final int puerto;
    private final String usuario;
    private final String password;

    public FTPManager(String servidor, int puerto, String usuario, String password) {
        this.servidor = servidor;
        this.puerto = puerto;
        this.usuario = usuario;
        this.password = password;
    }

    public boolean subirFactura(File archivoLocal, String carpetaRemota) {
        FTPClient ftp = new FTPClient();
        try {
            ftp.connect(servidor, puerto);
            if (!ftp.login(usuario, password)) {
                System.err.println("Credenciales FTP incorrectas.");
                return false;
            }
            ftp.enterLocalPassiveMode();
            ftp.setFileType(FTP.BINARY_FILE_TYPE);

            // Crear carpeta si no existe y cambiar a ella
            ftp.makeDirectory(carpetaRemota);
            ftp.changeWorkingDirectory(carpetaRemota);

            try (FileInputStream in = new FileInputStream(archivoLocal)) {
                return ftp.storeFile(archivoLocal.getName(), in);
            }
        } catch (IOException e) {
            System.err.println("Error en la subida FTP: " + e.getMessage());
            return false;
        } finally {
            desconectar(ftp);
        }
    }

    public boolean descargarFactura(String nombreFichero, String carpetaRemota, File archivoDestino) {
        FTPClient ftp = new FTPClient();
        try {
            ftp.connect(servidor, puerto);
            ftp.login(usuario, password);
            ftp.enterLocalPassiveMode();
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            ftp.changeWorkingDirectory(carpetaRemota);

            try (FileOutputStream out = new FileOutputStream(archivoDestino)) {
                return ftp.retrieveFile(nombreFichero, out);
            }
        } catch (IOException e) {
            System.err.println("Error en la descarga FTP: " + e.getMessage());
            return false;
        } finally {
            desconectar(ftp);
        }
    }

    public boolean verificarIntegridad(File original, File descargado) {
        return original.exists() && descargado.exists() && original.length() == descargado.length();
    }

    private void desconectar(FTPClient ftp) {
        try {
            if (ftp.isConnected()) {
                ftp.logout();
                ftp.disconnect();
            }
        } catch (IOException ignored) {}
    }
}