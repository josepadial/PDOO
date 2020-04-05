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
public class SorpresaPagarCobrar extends Sorpresa{
    private String texto;
    private int valor;
    private Tablero tablero;
    
    SorpresaPagarCobrar(Tablero tablero, int valor, String texto){
        this.tablero = tablero;
        this.valor = valor;
        this.texto = texto;
    }
    
    @Override
    void aplicarJugador(int actual, ArrayList<Jugador> todos){
        if(todos.get(actual) != null){
            informe(actual,todos);
            todos.get(actual).modificarSaldo(valor);
        }
    }

    @Override
    public String toString() {
        return "SorpresaPagarCobrar{" + "texto=" + texto + ", valor=" + valor + ", tablero=" + tablero + '}';
    }
}
