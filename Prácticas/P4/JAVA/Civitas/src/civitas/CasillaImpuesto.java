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
public class CasillaImpuesto extends Casilla{
    private float importe;
    
    CasillaImpuesto(String nombre, float cantidad){
        super(nombre);
        this.importe = cantidad;
    }
    
    @Override
    void recibeJugador(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual,todos) == true){
            informe(actual,todos);
            todos.get(actual).pagaImpuesto(importe);
        }
    }

    @Override
    public String toString() {
        return "CasillaImpuesto{" + "importe=" + importe + '}';
    }
}
