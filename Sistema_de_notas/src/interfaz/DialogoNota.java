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

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;

public class DialogoNota extends JDialog {
    private JTextField campoTitulo;
    private JTextArea campoDescripcion;
    private JTextField campoCalificacion;
    private JComboBox<String> comboCategoria;
    private JTextField campoFecha;
    private Nota nota;
    private GestorNotas gestor;
    private VentanaPrincipal ventana;

    public DialogoNota(VentanaPrincipal owner, GestorNotas gestor, Nota nota, VentanaPrincipal ventana) {
        super(owner, nota == null ? "Agregar Nota" : "Editar Nota", true);
        this.gestor = gestor;
        this.nota = nota;
        this.ventana = ventana;
        initComponents();
        setLocationRelativeTo(owner);
        setSize(400, 350);
        setVisible(true);
    }

    private void initComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        campoTitulo = new JTextField(nota != null ? nota.getTitulo() : "", 20);
        campoDescripcion = new JTextArea(nota != null ? nota.getDescripcion() : "", 3, 20);
        campoCalificacion = new JTextField(nota != null ? String.valueOf(nota.getCalificacion()) : "", 10);
        comboCategoria = new JComboBox<>(new String[]{"Tarea", "Examen", "Proyecto"});
        if (nota != null) comboCategoria.setSelectedItem(nota.getCategoria());
        campoFecha = new JTextField(nota != null ? nota.getFecha().toString() : LocalDate.now().toString(), 10);

        int y = 0;
        addEtiquetaCampo("Título:", campoTitulo, gbc, y++);
        addEtiquetaCampo("Descripción:", new JScrollPane(campoDescripcion), gbc, y++);
        addEtiquetaCampo("Calificación:", campoCalificacion, gbc, y++);
        addEtiquetaCampo("Categoría:", comboCategoria, gbc, y++);
        addEtiquetaCampo("Fecha (AAAA-MM-DD):", campoFecha, gbc, y++);

        JPanel panelBotones = new JPanel();
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");

        btnGuardar.addActionListener(this::guardar);
        btnCancelar.addActionListener(e -> dispose());

        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        gbc.gridy = y;
        gbc.gridwidth = 2;
        add(panelBotones, gbc);
    }

    private void addEtiquetaCampo(String texto, JComponent componente, GridBagConstraints gbc, int y) {
        gbc.gridx = 0;
        gbc.gridy = y;
        add(new JLabel(texto), gbc);
        gbc.gridx = 1;
        add(componente, gbc);
    }

    private void guardar(ActionEvent e) {
        try {
            String titulo = campoTitulo.getText().trim();
            String descripcion = campoDescripcion.getText().trim();
            double calificacion = Double.parseDouble(campoCalificacion.getText().trim());
            String categoria = (String) comboCategoria.getSelectedItem();
            LocalDate fecha = LocalDate.parse(campoFecha.getText().trim());

            Nota notaNueva = new Nota(nota != null ? nota.getId() : generarId(), 
                                    titulo, descripcion, calificacion, categoria, fecha);

            if (nota == null) {
                gestor.agregarNota(notaNueva);
            } else {
                gestor.eliminarNota(nota.getId());
                gestor.agregarNota(notaNueva);
            }

            ventana.actualizarLista();
            ventana.actualizarPromedio();
            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "La calificación debe ser un número válido.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private int generarId() {
        return gestor.obtenerTodas().stream()
                .mapToInt(Nota::getId)
                .max()
                .orElse(0) + 1;
    }
}