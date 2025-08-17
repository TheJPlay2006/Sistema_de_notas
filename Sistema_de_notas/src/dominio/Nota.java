/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio;

/**
 *
 * @author TheJPlay2006
 */

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Nota {
    private int id;
    private String titulo;
    private String descripcion;
    private double calificacion;
    private String categoria;
    private LocalDate fecha;

    public Nota(int id, String titulo, String descripcion, double calificacion,
                String categoria, LocalDate fecha) {
        setId(id);
        setTitulo(titulo);
        setDescripcion(descripcion);
        setCalificacion(calificacion);
        setCategoria(categoria);
        setFecha(fecha);
    }

    // Getters
    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getDescripcion() { return descripcion; }
    public double getCalificacion() { return calificacion; }
    public String getCategoria() { return categoria; }
    public LocalDate getFecha() { return fecha; }

    // Setters con validación
    public void setId(int id) { this.id = id; }

    public void setTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new DominioException("El título no puede ser vacío");
        }
        this.titulo = titulo.trim();
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = (descripcion == null) ? "" : descripcion.trim();
    }

    public void setCalificacion(double calificacion) {
        if (calificacion < 0.0 || calificacion > 10.0) {
            throw new DominioException("La calificación debe estar entre 0.0 y 10.0");
        }
        this.calificacion = calificacion;
    }

    public void setCategoria(String categoria) {
        if (categoria == null || categoria.trim().isEmpty()) {
            throw new DominioException("La categoría no puede ser nula");
        }
        if (!List.of("Tarea", "Examen", "Proyecto").contains(categoria)) {
            throw new DominioException("Categoría inválida. Use: Tarea, Examen, Proyecto");
        }
        this.categoria = categoria;
    }

    public void setFecha(LocalDate fecha) {
        if (fecha == null) {
            throw new DominioException("La fecha no puede ser nula");
        }
        this.fecha = fecha;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Nota nota = (Nota) o;
        return id == nota.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("Nota{id=%d, título='%s', calificación=%.1f, categoría='%s', fecha=%s}",
                id, titulo, calificacion, categoria, fecha);
    }
}