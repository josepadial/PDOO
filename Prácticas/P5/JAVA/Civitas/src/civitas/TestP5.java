/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;
import java.util.ArrayList;
import java.util.Scanner;
import GUI.*;

/**
 *
 * @author Jose Padial
 */
public class TestP5 {
    
    public static void main(String[] args) {
        CivitasView vista = new CivitasView();
        
        Dado.createInstance(vista);
        Dado dado = Dado.getInstance();
        dado.setDebug(true);
        
        CapturaNombres nom = new CapturaNombres(vista,true);
        ArrayList<String> nombres = new ArrayList();
        nombres = nom.getNombres();
        
        /////////////////////////////////////////////////////
        for(int i = 0; i < nombres.size(); i++){
            if(nombres.get(i).isEmpty()){
                nombres.remove(i);
            }
        }
        
        nombres.remove(nombres.size() - 1);
        ////////////////////////////////////////////////////
        //Es necesario que se ejecute el código entre lineas comentadas porque si no aunque introduzcamos un nombre, el programa coge los 4 jugadores
        
        CivitasJuego juego = new CivitasJuego(nombres);
        Controlador controlador = new Controlador(juego,vista);
        vista.setCivitasJuego(juego);
        
        controlador.juega();
        
    }
    
}
