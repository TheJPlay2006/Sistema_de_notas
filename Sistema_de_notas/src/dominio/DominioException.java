package dominio;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author TheJPlay2006
 */

public class DominioException extends RuntimeException {
    public DominioException(String mensaje) {
        super(mensaje);
    }

    public DominioException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}