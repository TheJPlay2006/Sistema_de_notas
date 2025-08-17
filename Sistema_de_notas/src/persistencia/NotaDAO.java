/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

/**
 *
 * @author TheJPlay2006
 */

import dominio.Nota;

import java.util.List;

public interface NotaDAO {
    void guardar(Nota nota) throws PersistenciaException;
    List<Nota> obtenerTodas() throws PersistenciaException;
    void eliminar(int id) throws PersistenciaException;
}