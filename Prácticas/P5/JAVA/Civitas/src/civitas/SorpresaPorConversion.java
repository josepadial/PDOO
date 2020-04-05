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
public class SorpresaPorConversion extends Sorpresa{
    private String texto;
    private int fianza;

    public SorpresaPorConversion(String texto, int fianza) {
        this.texto = texto;
        this.fianza = fianza;
    }
    
    @Override
    void aplicarJugador(int actual, ArrayList<Jugador> todos){
        if(todos.get(actual) != null){
            informe(actual,todos);
            JugadorEspeculador especulador = todos.get(actual).convertirme(fianza);
            todos.set(actual, especulador);
        }
    }

    @Override
    public String toString() {
        return "SorpresaConvertirse{" + "texto=" + texto + ", fianza=" + fianza + '}';
    }
}
