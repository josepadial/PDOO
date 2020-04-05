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
public abstract class Sorpresa {
    private Diario diario = Diario.getInstance();
    
    Sorpresa(){
        
    }
    
    abstract void aplicarJugador(int actual, ArrayList<Jugador> todos);
 
    void informe(int actual, ArrayList<Jugador> todos){
        diario.ocurreEvento("Se aplica una sorpresa al jugador{" + todos.get(actual) );
    }
    
    public boolean jugadorCorrecto(int actual, ArrayList<Jugador> todos){
        if(todos.get(actual)!= null){
            return true;
        }
        else{
            return false;
        }
    }
    
}
    
