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
public class CasillaJuez extends Casilla{
    private static int carcel;
    
    CasillaJuez(String nombre, int numCasillaCarcel){
        super(nombre);
        CasillaJuez.carcel = numCasillaCarcel;
    }
    
    @Override
    void recibeJugador(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual,todos) == true){
            informe(actual,todos);
            todos.get(actual).encarcelar(carcel);
        }
    }

    @Override
    public String toString() {
        return "CasillaJuez{" + "Numero=" + carcel + '}';
    }
}
