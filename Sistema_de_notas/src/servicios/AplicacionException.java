package servicios;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author TheJPlay2006
 */

public class AplicacionException extends RuntimeException {
    public AplicacionException(String mensaje) {
        super(mensaje);
    }

    public AplicacionException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}