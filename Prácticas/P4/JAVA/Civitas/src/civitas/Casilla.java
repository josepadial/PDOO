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
public class Casilla {
    private String nombre;
    
    Casilla(String arg){;
        this.nombre = arg;
    }   
    
    public String getNombre(){
        return nombre;
    }
    
    void informe(int actual, ArrayList<Jugador> todos){
        Diario diario = Diario.getInstance();
        diario.ocurreEvento("El jugador {" + todos.get(actual) +"} a caido en la casilla" + toString());
        toString();
    }
    
    public boolean jugadorCorrecto(int actual, ArrayList<Jugador> todos){
        if(todos.get(actual) != null){
            return true;
        }
        else{
            return false;
        }
    }
    
    void recibeJugador(int actual, ArrayList<Jugador> todos){
       informe(actual,todos);
    }

    @Override
    public String toString() {
        return "Casilla {" + "nombre = " + nombre + '}';
    }
}
