/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JuegoTexto;

/**
 *
 * @author Jose Padial
 */

import civitas.CivitasJuego;
import civitas.Dado;
import java.util.ArrayList;

public class JuegoTexto {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        VistaTextual vista= new VistaTextual();
        ArrayList<String> nombres = new ArrayList<>();
        nombres.add("Jose");
        nombres.add("Alex");
        nombres.add("Ruben");
        Dado.getInstance().setDebug(true);
        CivitasJuego juego= new CivitasJuego(nombres); 
        Controlador controller= new Controlador(juego, vista);
        controller.juega();
    }
    
}