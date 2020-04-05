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
public class SorpresaPorCasaHotel extends Sorpresa{
    private String texto;
    private int valor;
    private Tablero tablero;
    
    SorpresaPorCasaHotel(Tablero tablero, int valor, String texto){
        this.tablero = tablero;
        this.valor = valor;
        this.texto = texto;
    }
    
    @Override
    void aplicarJugador(int actual, ArrayList<Jugador> todos){
        if(todos.get(actual) != null){
            informe(actual,todos);
            int numCasasHoteles = todos.get(actual).cantidadCasasHoteles();
            int importe = valor * numCasasHoteles;
            todos.get(actual).modificarSaldo(importe);
        }
    }

    @Override
    public String toString() {
        return "SorpresaPorCasaHotel{" + "texto=" + texto + ", valor=" + valor + ", tablero=" + tablero + '}';
    } 
}
