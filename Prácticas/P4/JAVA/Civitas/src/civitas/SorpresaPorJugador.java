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
public class SorpresaPorJugador extends Sorpresa{
    private String texto;
    private int valor;
    private Tablero tablero;
    
    SorpresaPorJugador(Tablero tablero, int valor, String texto){
        this.tablero = tablero;
        this.valor = valor;
        this.texto = texto;
    }
    
    @Override
    void aplicarJugador(int actual, ArrayList<Jugador> todos){
        if(todos.get(actual) != null){
            informe(actual,todos);
            Sorpresa sorpresa = new SorpresaPagarCobrar(tablero,(valor * -1),"Jugadores Pagan");
           
            for(int i = 0; i < todos.size(); i++){
                if(todos.get(actual) != todos.get(i)){
                    sorpresa.aplicarJugador(i,todos);
                }
            }
            
            int numJug = todos.size() - 1;
            Sorpresa sorpresa2 = new SorpresaPagarCobrar(tablero,(valor * numJug),"Jugador recibe");
            sorpresa2.aplicarJugador(actual, todos);
        }
    }

    @Override
    public String toString() {
        return "SorpresaPorJugador{" + "texto=" + texto + ", valor=" + valor + ", tablero=" + tablero + '}';
    }
}
