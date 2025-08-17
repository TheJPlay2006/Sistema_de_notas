/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicios;

/**
 *
 * @author TheJPlay2006
 */

import dominio.DominioException;
import dominio.Nota;
import persistencia.NotaDAO;
import persistencia.PersistenciaException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GestorNotas {
    private List<Nota> notas;
    private NotaDAO notaDAO;

    public GestorNotas(NotaDAO notaDAO) {
        this.notas = new ArrayList<>();
        this.notaDAO = notaDAO;
        cargarDesdeBaseDeDatos();
    }

    private void cargarDesdeBaseDeDatos() {
        try {
            notas = notaDAO.obtenerTodas();
        } catch (PersistenciaException e) {
            throw new AplicacionException("No se pudo cargar las notas desde la base de datos", e);
        }
    }

    public void agregarNota(Nota nota) {
        if (notas.stream().anyMatch(n -> n.getId() == nota.getId())) {
            throw new DominioException("Ya existe una nota con el ID: " + nota.getId());
        }
        try {
            notaDAO.guardar(nota);
            notas.add(nota);
        } catch (PersistenciaException e) {
            throw new AplicacionException("Error al guardar la nota en la base de datos", e);
        }
    }

    public void eliminarNota(int id) {
        try {
            notaDAO.eliminar(id);
            notas.removeIf(n -> n.getId() == id);
        } catch (PersistenciaException e) {
            throw new AplicacionException("Error al eliminar la nota", e);
        }
    }

    public List<Nota> obtenerTodas() {
        return new ArrayList<>(notas);
    }

    public List<Nota> buscarPorCategoria(String categoria) {
        return notas.stream()
                .filter(n -> n.getCategoria().equals(categoria))
                .collect(Collectors.toList());
    }

    public double calcularPromedioGeneral() {
        return notas.stream()
                .mapToDouble(Nota::getCalificacion)
                .average()
                .orElse(0.0);
    }
}