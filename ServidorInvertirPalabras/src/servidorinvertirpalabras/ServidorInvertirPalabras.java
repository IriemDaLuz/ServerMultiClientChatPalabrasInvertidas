package servidorinvertirpalabras;

import java.io.*;
import java.net.*;

public class ServidorInvertirPalabras {

    public static void main(String[] args) {
        int puerto = 12345; 

        try (ServerSocket servidorSocket = new ServerSocket(puerto)) {
            System.out.println("Servidor iniciado. Escuchando en el puerto: " + puerto);

            while (true) {
                Socket clienteSocket = servidorSocket.accept(); 
                System.out.println("Cliente conectado desde: " + clienteSocket.getInetAddress() + ":" + clienteSocket.getPort());

                Thread clienteHilo = new Thread(new ManejadorCliente(clienteSocket));
                clienteHilo.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ManejadorCliente implements Runnable {
    private Socket clienteSocket;
    private int clienteId;
    private static int contadorClientes = 0;

    public ManejadorCliente(Socket socket) {
        this.clienteSocket = socket;
        this.clienteId = ++contadorClientes;
    }

    @Override
    public void run() {
        try (
             BufferedReader in = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clienteSocket.getOutputStream(), true)){

            String palabra;
            while ((palabra = in.readLine()) != null) {
                System.out.println("Cliente " + clienteId + " ha enviado: " + palabra);

                if (palabra.equalsIgnoreCase("quit")) {
                    System.out.println("Cliente " + clienteId + " ha finalizado la conexion.");
                    break;
                }

                String palabraInvertida = new StringBuilder(palabra).reverse().toString();
                out.println(palabraInvertida);
                System.out.println("Respuesta enviada al cliente " + clienteId + ": " + palabraInvertida);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clienteSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}