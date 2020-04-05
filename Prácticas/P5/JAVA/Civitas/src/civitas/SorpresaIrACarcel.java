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
public class SorpresaIrACarcel extends Sorpresa{
    private Tablero tablero;
    
    SorpresaIrACarcel(Tablero tablero){
        this.tablero = tablero;
    }
    
    @Override
    void aplicarJugador(int actual, ArrayList<Jugador> todos){
        if(todos.get(actual) != null){
            informe(actual,todos);
            todos.get(actual).encarcelar(tablero.getCarcel());
        }
    }

    @Override
    public String toString() {
        return "SorpresaIrACarcel{" + "tablero=" + tablero + '}';
    }
}
