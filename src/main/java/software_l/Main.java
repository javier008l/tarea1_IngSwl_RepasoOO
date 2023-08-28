package software_l;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    static int lineasTotales;
    static int totalCoincidenciasArchivo;
    static int totalCoincidencias;

    public static void main(String[] args) {
        Scanner Ruta = new Scanner(System.in);
        System.out.println("Ingrese la ruta del archivo: ");
        String directorio = Ruta.nextLine();
        System.out.println("");

        File ruta = new File(directorio);

        Scanner Busca = new Scanner(System.in);
        System.out.println("Ingrese palabra a buscar: ");
        String palabra = Busca.nextLine();
        System.out.println("");

        if (ruta.exists() && ruta.isDirectory()) {
            buscarEnCarpeta(ruta, palabra);

            if (totalCoincidencias > 0) {
                System.out.println("En total se encontró la palabra: " + palabra + ", " + totalCoincidenciasArchivo + " veces");
            }
        } else {
            System.out.println("La carpeta no se encontró.");
        }
    }

    public static void buscarEnCarpeta(File carpeta, String palabra) {
        try {
            File[] archivos = carpeta.listFiles();

            if (archivos != null) {
                for (File archivo : archivos) {
                    String error = esExtensionPermitida(archivo);
                    if (error == null) {
                        totalCoincidencias = 0;
                        buscarPalabra(archivo, palabra);
                        System.out.println(archivo.getName() + " " + totalCoincidencias + " veces");

                        totalCoincidenciasArchivo += totalCoincidencias;

                    } else {
                        System.out.println("No se encontraron Archivos de texto en la carpeta: " + error);
                    }
                    if (archivo.isDirectory()) {
                        buscarEnCarpeta(archivo, palabra);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static String esExtensionPermitida(File archivo) {
        String nombreArchivo = archivo.getName();
        String extension = nombreArchivo.substring(nombreArchivo.lastIndexOf(".") + 1);
        if (extension.equals("txt") || extension.equals("xml") ||
                extension.equals("json") || extension.equals("csv")) {
            return null;
        } else {
            return "Extensión no permitida: ." + extension;
        }
    }

    public static void buscarPalabra(File archivo, String palabra) {
        try {
            if (archivo.exists()) {
                FileReader fileReader = new FileReader(archivo);
                int caracter;

                while ((caracter = fileReader.read()) != -1) {
                    if (caracter == ' ' || caracter == '\n' || caracter == '\r') {
                        lineasTotales++;
                    }
                    char letra = (char) caracter;

                    if (Character.isLetterOrDigit(letra)) {
                        StringBuilder palabraActual = new StringBuilder();
                        palabraActual.append(letra);

                        while ((caracter = fileReader.read()) != -1 && Character.isLetterOrDigit((char) caracter)) {
                            palabraActual.append((char) caracter);
                        }
                        if (palabraActual.toString().equals(palabra)) {
                            totalCoincidencias++;
                        }
                    }
                }
                fileReader.close();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
