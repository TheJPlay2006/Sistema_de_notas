/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaz;

/**
 *
 * @author TheJPlay2006
 */

import dominio.Nota;
import servicios.GestorNotas;
import servicios.AplicacionException;
import persistencia.NotaDAOImpl;
import utilidades.ExportadorMarkdown;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class VentanaPrincipal extends JFrame {
    private DefaultListModel<String> modeloLista;
    private JList<String> listaNotas;
    private JLabel etiquetaPromedio;
    private GestorNotas gestor;

    public VentanaPrincipal() {
        inicializarGestor();
        configurarVentana();
        crearComponentes();
        actualizarLista();
        actualizarPromedio();
    }

    private void inicializarGestor() {
        try {
            gestor = new GestorNotas(new NotaDAOImpl());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error al conectar con la base de datos. Verifique SQL Server.",
                "Error de conexión", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    private void configurarVentana() {
        setTitle("Gestión de Notas Académicas");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void crearComponentes() {
        modeloLista = new DefaultListModel<>();
        listaNotas = new JList<>(modeloLista);
        add(new JScrollPane(listaNotas), BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        JButton btnAgregar = new JButton("Agregar");
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnExportar = new JButton("Exportar a Markdown");

        btnAgregar.addActionListener(e -> new DialogoNota(this, gestor, null, this));
        btnEditar.addActionListener(e -> editarSeleccionada());
        btnEliminar.addActionListener(e -> eliminarSeleccionada());
        btnExportar.addActionListener(e -> exportarAMarkdown());

        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnExportar);
        add(panelBotones, BorderLayout.SOUTH);

        etiquetaPromedio = new JLabel("Promedio: --");
        add(etiquetaPromedio, BorderLayout.NORTH);
    }

    private void editarSeleccionada() {
        int indice = listaNotas.getSelectedIndex();
        if (indice == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una nota para editar.");
            return;
        }
        String item = modeloLista.getElementAt(indice);
        int id = Integer.parseInt(item.split(" - ")[0]);
        Nota nota = gestor.obtenerTodas().stream()
                .filter(n -> n.getId() == id)
                .findFirst()
                .orElse(null);
        if (nota != null) {
            new DialogoNota(this, gestor, nota, this);
        }
    }

    private void eliminarSeleccionada() {
        int indice = listaNotas.getSelectedIndex();
        if (indice == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una nota para eliminar.");
            return;
        }
        String item = modeloLista.getElementAt(indice);
        int id = Integer.parseInt(item.split(" - ")[0]);

        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Eliminar nota ID " + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                gestor.eliminarNota(id);
                actualizarLista();
                actualizarPromedio();
            } catch (AplicacionException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }

    private void exportarAMarkdown() {
        try {
            ExportadorMarkdown.exportar(gestor.obtenerTodas(), "notas.md");
            JOptionPane.showMessageDialog(this, "Notas exportadas a 'notas.md'");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al exportar: " + e.getMessage());
        }
    }

    public void actualizarLista() {
        modeloLista.clear();
        for (Nota nota : gestor.obtenerTodas()) {
            modeloLista.addElement(nota.getId() + " - " + nota.getTitulo() + 
                                 " (" + nota.getCalificacion() + ")");
        }
    }

    public void actualizarPromedio() {
        double promedio = gestor.calcularPromedioGeneral();
        etiquetaPromedio.setText(String.format("Promedio General: %.2f", promedio));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
            } catch (Exception e) { /* ignorar */ }
            new VentanaPrincipal().setVisible(true);
        });
    }
}