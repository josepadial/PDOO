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
    private ArrayList<Casilla> casillas;
    private int porSalida;
    private boolean tieneJuez;
    
    public Tablero(int num){
        
        if(num >=1){
           this.numCasillaCarcel = num;
        }else{
           this.numCasillaCarcel = 1;
        }
        
        this.casillas = new ArrayList<>();
        this.aniadeCasillaSalida();
        
        this.porSalida = 0;
        this.tieneJuez = false;
        
    }
    
    private void aniadeCasillaSalida(){
        casillas.add(new Casilla("Salida"));
    }
    
    private boolean correcto(){
        if(casillas.size() > numCasillaCarcel && tieneJuez == true){
            return true;
        }else{
            return false;
        }
    }
    
    private boolean correcto(int numCasilla){
        if(correcto() == true && numCasilla <= casillas.size()){
            return true;
        }else{
            return false;
        }
    }
    
    int getCarcel(){
        return numCasillaCarcel;
    }
    
    int getPorSalida(){
        return porSalida;
    }
    
    void aniadeCasilla(Casilla casilla){
        if(casillas.size() == numCasillaCarcel){
            casillas.add(new Casilla("Carcel"));
            casillas.add(casilla);
        }else{
            casillas.add(casilla);
            if(casillas.size() == numCasillaCarcel){
                casillas.add(new Casilla("Carcel"));
            }
        }   
    }
    
    void aniadeJuez(){
        if(tieneJuez == false){
            aniadeCasilla(new CasillaJuez("Juez",numCasillaCarcel));
            tieneJuez = true;
        }
    }
    
    Casilla getCasilla(int numCasilla){
        return casillas.get(numCasilla);
    }
    
    int nuevaPosicion(int actual, int tirada){
        int aux = actual+tirada;
        int posNew = 0;
        if(aux >= casillas.size()){
            posNew = aux % casillas.size();
            if(actual > posNew){
            porSalida = porSalida + 1;
            }
            return posNew;
        }
        else{
            return aux;
        }
    }
    
    int calcularTirada(int origen, int destino){
        int aux = destino - origen;
        
        if(aux < 0 ){
            aux = aux + casillas.size();
            return aux;
        }else{
             return aux;       
             }
    }
    
    @Override
    public String toString() {
        return "Tablero{" + "casillas=" + casillas + ", carcel=" + numCasillaCarcel + '}';
    }
}
