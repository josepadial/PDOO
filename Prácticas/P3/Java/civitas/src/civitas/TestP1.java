/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

import java.util.Scanner;
import java.util.ArrayList;

/**
 *
 * @author Jose Padial
 */
public class TestP1 {

    /**
     * @param args the command line arguments
     */
    
    private static final int numJugadores = 4;
    private static Dado dado = Dado.getInstance();
    private static final Scanner in = new Scanner(System.in);
    
    private static ArrayList<String> getNombreJugadores(){
        ArrayList<String>nombres = new ArrayList();
        String nombre;
        
         for(int i = 0; i < numJugadores; ++i){
            System.out.print("Introduce el nombre del jugador " + (i+1) + ": ");
            nombre = in.nextLine();
            nombres.add(nombre);
        }
        System.out.println();
        
        return nombres;
    }
    
    public static void main(String[] args) {
        ArrayList<String>nombres = getNombreJugadores();
        CivitasJuego juego = new CivitasJuego(nombres);
        
        juego.getJugadorActual().moverACasilla(dado.tirar() + juego.getJugadorActual().getNumCasillaActual());
        System.out.print("\nValor del dado: "+dado.getUltimoResultado());
        
        juego.getJugadorActual().comprar(juego.getCasillaActual().getTituloPropiedad());
        juego.construirCasa(8);
        
        System.out.print("\n"+juego.infoJugadorTexto());
        System.out.print("\n"+juego.getCasillaActual().toString());
        
        juego.getJugadorActual().moverACasilla(dado.tirar() + juego.getJugadorActual().getNumCasillaActual());
        System.out.print("\nValor del dado: "+dado.getUltimoResultado());
        
        System.out.print("\n"+juego.infoJugadorTexto());
        System.out.print("\n"+juego.getCasillaActual().toString());
        
        juego.getJugadorActual().encarcelar(10);
        System.out.print("\nEncarcelado");
        
        System.out.print("\n"+juego.infoJugadorTexto());
        System.out.print("\n"+juego.getCasillaActual().toString());
    }
    
}
