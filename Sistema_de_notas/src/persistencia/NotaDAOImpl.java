package persistencia;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author TheJPlay2006
 */
import dominio.Nota;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class NotaDAOImpl implements NotaDAO {

    // 🔥 TU FORMA DE CONECTAR: integratedSecurity + SQLEXPRESS
    private static final String URL = "jdbc:sqlserver://localhost\\SQLEXPRESS;"
            + "databaseName=NotasDB;"
            + "integratedSecurity=true;"
            + "encrypt=true;"
            + "trustServerCertificate=true;";

    /**
     * Obtiene una conexión a SQL Server usando autenticación integrada de Windows.
     */
    private Connection conectar() throws PersistenciaException {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(URL);
        } catch (ClassNotFoundException e) {
            throw new PersistenciaException(
                "❌ Driver JDBC no encontrado. Asegúrese de tener el JAR de Microsoft en 'lib/'.", e);
        } catch (SQLException e) {
            throw new PersistenciaException(
                "❌ Error de conexión: revise que SQL Server (SQLEXPRESS) esté ejecutándose.", e);
        }
    }

    @Override
    public void guardar(Nota nota) throws PersistenciaException {
        String sql = "INSERT INTO Notas (titulo, descripcion, calificacion, categoria, fecha) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nota.getTitulo());
            ps.setString(2, nota.getDescripcion());
            ps.setDouble(3, nota.getCalificacion());
            ps.setString(4, nota.getCategoria());
            ps.setDate(5, Date.valueOf(nota.getFecha()));

            ps.executeUpdate();
        } catch (PersistenciaException e) {
            throw e;
        } catch (SQLException e) {
            throw new PersistenciaException("Error al guardar nota: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Nota> obtenerTodas() throws PersistenciaException {
        List<Nota> notas = new ArrayList<>();
        String sql = "SELECT id, titulo, descripcion, calificacion, categoria, fecha FROM Notas";
        try (Connection conn = conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Nota nota = new Nota(
                    rs.getInt("id"),
                    rs.getString("titulo"),
                    rs.getString("descripcion"),
                    rs.getDouble("calificacion"),
                    rs.getString("categoria"),
                    rs.getDate("fecha").toLocalDate()
                );
                notas.add(nota);
            }
        } catch (PersistenciaException e) {
            throw e;
        } catch (SQLException e) {
            throw new PersistenciaException("Error al obtener notas: " + e.getMessage(), e);
        }
        return notas;
    }

    @Override
    public void eliminar(int id) throws PersistenciaException {
        String sql = "DELETE FROM Notas WHERE id = ?";
        try (Connection conn = conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int filas = ps.executeUpdate();
            if (filas == 0) {
                throw new PersistenciaException("No se encontró la nota con ID: " + id);
            }
        } catch (PersistenciaException e) {
            throw e;
        } catch (SQLException e) {
            throw new PersistenciaException("Error al eliminar nota: " + e.getMessage(), e);
        }
    }
}