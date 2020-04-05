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
public class CasillaCalle extends Casilla{
    private TituloPropiedad titulo;
    
    CasillaCalle(TituloPropiedad titulo){
        super(titulo.getNombre());
        this.titulo = titulo;
    }
    
    TituloPropiedad getTituloPropiedad(){
        return titulo;
    }
    
    @Override
     void recibeJugador(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual,todos) == true){
            super.informe(actual,todos);
            if(titulo.tienePropietario() == false){
                todos.get(actual).puedeComprarCasilla();
            }
            else{
                titulo.tramitarAlquiler(todos.get(actual));
            }
        }
    }

    @Override
    public String toString() {
        return "CasillaCalle{" + "titulo=" + titulo + '}';
    }
}
