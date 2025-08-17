/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilidades;

/**
 *
 * @author TheJPlay2006
 */

import dominio.Nota;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ExportadorMarkdown {
    public static void exportar(List<Nota> notas, String ruta) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ruta))) {
            bw.write("# Notas Académicas\n\n");
            bw.write("| ID | Título | Calificación | Categoría | Fecha      |\n");
            bw.write("|----|--------|--------------|-----------|------------|\n");
            for (Nota n : notas) {
                bw.write(String.format("| %d | %s | %.1f | %s | %s |\n",
                    n.getId(), n.getTitulo(), n.getCalificacion(), n.getCategoria(), n.getFecha()));
            }
        }
    }
}