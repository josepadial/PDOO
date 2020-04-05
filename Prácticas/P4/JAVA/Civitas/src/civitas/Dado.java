/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

import java.util.Random;

/**
 *
 * @author Jose Padial
 */
public class Dado {
    private  Random random;
    private int ultimoResultado;
    private boolean debug;
    
    private static Dado instance = new Dado();
    private static final int SalidaCarcel = 5;
    
    private Dado(){
        random = new Random();
        ultimoResultado = -1;
        debug = false;
    }
    
    static public Dado getInstance(){
        return instance;
    }
    
    int tirar(){
        int numero = 0;
        if(debug == false){
            numero = random.nextInt(6) + 1;
            ultimoResultado = numero;
            return numero;
        }else{
            return 1;
        }
    }
    
    boolean salgoDeCarcel(){
        int num = tirar();
        
        if(num == SalidaCarcel){
            return true;
        }
        else{
            return false;
        }
    }
    
    int quienEmpieza(int n){
        int jugador = 0;
        jugador = random.nextInt(6) + 1;
        return jugador;
    }
    
    void setDebug(boolean d){
        debug = d;
        Diario diario = Diario.getInstance();
        diario.ocurreEvento("Se ha modficado el estado debug a" + debug + " ");
    }
    
    int getUltimoResultado(){
        return ultimoResultado;
    }
}
