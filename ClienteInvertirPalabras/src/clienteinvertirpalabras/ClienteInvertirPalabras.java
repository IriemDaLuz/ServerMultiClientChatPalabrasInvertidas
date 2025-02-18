package clienteinvertirpalabras;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClienteInvertirPalabras {

    public static void main(String[] args) {
        String servidorDireccion = "localhost";
        int puerto = 12345;

        try (
             Socket socket = new Socket(servidorDireccion, puerto);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Conectado al servidor. Escribe palabras para invertirlas (o 'quit' para salir).");

            while (true) {
                System.out.print("Palabra: ");
                String palabra = scanner.nextLine();

                out.println(palabra);
                if (palabra.equalsIgnoreCase("quit")) {
                    System.out.println("Conexión finalizada.");
                    break;
                }

                String respuesta = in.readLine();
                System.out.println("Respuesta del servidor: " + respuesta);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
