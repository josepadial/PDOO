/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

/**
 *
 * @author Jose Padial
 */
public class Casilla {
    private String nombre;
    
    Casilla(String cadena){
        nombre = cadena;
    }

    String getNombre(){
        return nombre;
    }
}
