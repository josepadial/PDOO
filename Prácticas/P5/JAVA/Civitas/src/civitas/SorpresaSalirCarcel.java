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
public class SorpresaSalirCarcel extends Sorpresa{
    private MazoSorpresas mazo;
    
    SorpresaSalirCarcel(MazoSorpresas mazo){
        this.mazo = mazo;
    }
    
    @Override
    void aplicarJugador(int actual, ArrayList<Jugador> todos){
        boolean laTiene = false;
        
        if(todos.get(actual) != null){
            informe(actual,todos);
            for(int i = 0; i < todos.size(); i++){
                if(todos.get(i).tieneSalvoConducto() == true){
                    laTiene = true;
                }
            }
          
            if(laTiene == false){
                todos.get(actual).obtenerSalvoconducto(this);
                this.salirDelMazo();
            }
            
        }
    }
    
    void salirDelMazo(){
        mazo.inhabilitarCartaEspecial(this);
    }
    
    void usada(){
        mazo.habilitarCartaEspecial(this);
    }

    @Override
    public String toString() {
        return "SorpresaSalirCarcel{" + "mazo=" + mazo + '}';
    }
}
