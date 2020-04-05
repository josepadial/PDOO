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
public class SorpresaIrACasilla extends Sorpresa{
    private String texto;
    private int valor;
    private Tablero tablero;
    
    SorpresaIrACasilla(Tablero tablero, int valor, String texto){
        this.tablero = tablero;
        this.valor = valor;
        this.texto = texto;
        
    }
    
    @Override
    void aplicarJugador(int actual, ArrayList<Jugador> todos){
        if(todos.get(actual) != null){
            informe(actual,todos);
            int numCasillaActual = todos.get(actual).getNumCasillaActual();
            int tirada = tablero.calcularTirada(numCasillaActual, valor);
            int newPos = tablero.nuevaPosicion(numCasillaActual, tirada);
            todos.get(actual).moverACasilla(newPos);
            tablero.getCasilla(valor).recibeJugador(actual, todos);
        }
    }

    @Override
    public String toString() {
        return "SorpresaIrCasilla{" + "texto=" + texto + ", valor=" + valor + ", tablero=" + tablero + '}';
    }
}
