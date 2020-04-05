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
public class Tablero {
    private int numCasillaCarcel;
    private ArrayList<Casilla>casillas;
    private int porSalida;
    private boolean tieneJuez;
    
    public Tablero(int numCasillaCarcel){
        this.numCasillaCarcel = numCasillaCarcel;
        this.casillas = new ArrayList();
        this.porSalida = 0;
        this.tieneJuez = false;
    }
    
    private boolean correcto(){
        if(casillas.size() > numCasillaCarcel && tieneJuez == true)
            return true;
        return false;
    }
    
    private boolean correcto(int numCasilla){
        if(correcto() && casillas.size() > numCasilla)
            return true;
        return false;
    }

    int getCarcel() {
        return numCasillaCarcel;
    }

     int getPorSalida() {
        return porSalida;
    }
    
    void añadeCasilla (Casilla casilla){
        if(casillas.size()+1 == numCasillaCarcel)
            casillas.add(new Casilla("Cárcel"));
        casillas.add(casilla);
        if(casillas.size()+1 == numCasillaCarcel)
            casillas.add(new Casilla("Cárcel"));
    }
    
    void añadeJuez(){
        if(tieneJuez == false){
            casillas.add(new Casilla(10,"Juez"));
            tieneJuez = true;
        }
    }
    
    Casilla getCasilla (int numCasilla){ 
        if(correcto(numCasilla))
            return casillas.get(numCasilla);
        return null;
    }
    
    int nuevaPosicion(int actual, int tirada){
        int nuevaPos;
        if(!correcto())
            return -1;
        else{
            nuevaPos = (actual+tirada)%casillas.size();
            if(actual > nuevaPos)
                porSalida++;
        }
        return nuevaPos;
    }
    
    int calcularTirada (int origen, int destino){
        int valor = destino - origen;
        
        if(valor < 0)
            valor = valor + casillas.size();
        return valor;
    }
}
