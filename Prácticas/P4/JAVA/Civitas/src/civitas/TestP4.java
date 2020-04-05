/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;
import java.util.ArrayList;
import java.util.Scanner;
import juegoTexto.*;
import juegoTexto.VistaTextual;

/**
 *
 * @author Jose Padial
 */
public class TestP4 {
    
    static private ArrayList getNombreJugadores(){
        
        Scanner in = new Scanner (System.in);
        ArrayList<String> nombres = new ArrayList<>();
        int cont = 1;
        
        System.out.println("Introduzca el numero de jugadores");
        int condicion = in.nextInt();
        
        in.nextLine(); //es necesario poner un nextLine justo despues de un nextInt para evitar que nos recupere del buffer el \n y no nos lea el primer nombre
        
        if(condicion >= 2 && condicion <= 4){
         while(cont <= condicion){
             System.out.println("Introduzca nombre de jugador " + cont  );
             String s = in.nextLine();
             nombres.add(s);
             cont = cont + 1;
         } 
        } else System.out.println("El numero de jugadores no es correcto");
        return nombres;
    }
    
    
    static private CivitasJuego juego = new CivitasJuego(getNombreJugadores());
    
    public static void main(String[] args) {
        
       /*System.out.println(juego.tablero);
        juego.getJugadorActual().moverACasilla(dado.tirar() + juego.getJugadorActual().getNumCasillaActual());
        System.out.print("\nValor del dado: "+dado.getUltimoResultado()+"\n");
        System.out.println(juego.getJugadorActual());
        
        juego.getJugadorActual().comprar(juego.getCasillaActual().getTituloPropiedad());
        juego.construirCasa(8);
        
        System.out.print("\n"+juego.infoJugadorTexto());
        System.out.print("\n"+juego.getCasillaActual().toString());
        
        
        juego.getJugadorActual().moverACasilla(dado.tirar() + juego.getJugadorActual().getNumCasillaActual());
        System.out.print("\nValor del dado: "+dado.getUltimoResultado());
        
        System.out.print("\n"+juego.infoJugadorTexto());
        System.out.print("\n"+juego.getCasillaActual().toString());
       
        
        juego.getJugadorActual().encarcelar(8);
        System.out.print("\n"+juego.infoJugadorTexto());*/
       VistaTextual vista = new VistaTextual();
       Controlador controlador = new Controlador(juego,vista);
       controlador.juega();
       
    }
    
}
