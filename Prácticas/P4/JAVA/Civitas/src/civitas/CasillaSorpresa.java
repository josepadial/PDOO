/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

import java.util.ArrayList;

/**
 *
 * @author Jose Padial
 */
public class CasillaSorpresa extends Casilla{
    MazoSorpresas mazo;
    Sorpresa sorpresa;
    
    CasillaSorpresa(String nombre, MazoSorpresas mazo){
        super(nombre);
        this.mazo = mazo;
        this.sorpresa = null;
    }
    
    @Override
    void recibeJugador(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual,todos) == true){
            sorpresa = mazo.siguiente();
            informe(actual,todos);
            sorpresa.aplicarJugador(actual, todos);
        }
    }

    @Override
    public String toString() {
        return "CasillaSorpresa{" + "mazo=" + mazo.toString() + ", sorpresa=" + sorpresa.toString() + '}';
    }
   
}
