package com.autociclo.utils;

import net.sf.jasperreports.engine.JasperCompileManager;

/**
 * Clase temporal para compilar archivos .jrxml a .jasper
 * Se ejecuta una vez para generar los archivos compilados
 */
public class CompiladorInformes {

    public static void main(String[] args) {
        // Obtener la ruta absoluta del directorio del proyecto
        String userDir = System.getProperty("user.dir");
        // Si user.dir termina con "/app", no lo agregamos de nuevo
        String baseDir = userDir.endsWith("/app")
            ? userDir + "/src/main/resources/informes/"
            : userDir + "/app/src/main/resources/informes/";

        String[] informes = {
            "InformePiezas",
            "InformeVehiculos",
            "InformeInventario"
        };

        System.out.println("=== Compilador de Informes JasperReports ===\n");
        System.out.println("Directorio base: " + baseDir + "\n");

        for (String informe : informes) {
            try {
                String rutaJRXML = baseDir + informe + ".jrxml";
                String rutaJasper = baseDir + informe + ".jasper";

                System.out.println("Compilando: " + informe + ".jrxml...");
                JasperCompileManager.compileReportToFile(rutaJRXML, rutaJasper);
                System.out.println("✓ Compilado exitosamente: " + informe + ".jasper\n");

            } catch (Exception e) {
                System.err.println("✗ Error al compilar " + informe + ": " + e.getMessage());
                e.printStackTrace();
            }
        }

        System.out.println("\n=== Compilación finalizada ===");
    }
}
