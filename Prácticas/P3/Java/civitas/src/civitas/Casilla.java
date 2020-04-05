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
    private float importe;
    private static int carcel;
    private TipoCasilla tipo;
    private TituloPropiedad tituloPropiedad;
    private Sorpresa sorpresa;
    private MazoSorpresas mazo;
    private Diario diario = Diario.getInstance();
    
    Casilla(String cadena){
        init();
        nombre = cadena;
        this.tipo = TipoCasilla.DESCANSO;
    }
    
    Casilla(TituloPropiedad titulo){
        init();
        this.tituloPropiedad = titulo;
        this.nombre = titulo.getNombre();
        this.tipo = TipoCasilla.CALLE;
        this.importe = titulo.getPrecioCompra();
    }
    
    Casilla(float cantidad, String nombre){
        init();
        this.importe = cantidad;
        this.nombre = nombre;
        this.tipo = TipoCasilla.IMPUESTO;
    }
    
    Casilla(int numCasillaCarcel, String nombre){
        init();
        this.carcel = numCasillaCarcel;
        this.nombre = nombre;
        this.tipo = TipoCasilla.JUEZ;
    }
    
    Casilla(MazoSorpresas mazo, String nombre){
        init();
        this.mazo = mazo;
        this.nombre = nombre;
        this.tipo = TipoCasilla.SORPRESA;
    }

    public String getNombre(){
        return nombre;
    }

    TituloPropiedad getTituloPropiedad() {
        return tituloPropiedad;
    }
    
    private void informe(int iactual, ArrayList<Jugador> todos){
        diario.ocurreEvento("En la casilla " + toString() + " esta " + todos.get(iactual).toString());
    }
    
    private void init(){
        this.carcel = -1;
        this.nombre = "";
        this.importe = 0;
        this.tipo=TipoCasilla.DESCANSO;
        this.tituloPropiedad= null;
        this.sorpresa= null;
        this.mazo=null;
    }
    
    public boolean juagadorCorrecto(int iactual, ArrayList<Jugador> todos){
        return todos.size() > iactual;
    }
    
    void recibeJugador(int iactual, ArrayList<Jugador> todos){
        switch(tipo){
            case CALLE:
                recibeJugador_calle(iactual, todos);
                break;
            case IMPUESTO:
                recibeJugador_impuesto(iactual, todos);
                break;
            case JUEZ:
                recibeJugador_juez(iactual, todos);
                break;  
            case SORPRESA:
                recibeJugador_sorpresa(iactual, todos);
                break;  
            default:
                informe(iactual, todos);
                break;
        }
    }
    
    private void recibeJugador_calle(int iactual, ArrayList<Jugador> todos){
        if(juagadorCorrecto(iactual, todos)){
            informe(iactual, todos);
            Jugador nuevo = todos.get(iactual);
            if(!tituloPropiedad.tienePropietario()){
                nuevo.puedeComprarCasilla();
            }
            else{
                tituloPropiedad.tramitarAlquiler(nuevo);
            }
        }
    }
    
    private void recibeJugador_impuesto(int iactual, ArrayList<Jugador> todos){
        if(juagadorCorrecto(iactual, todos)){
            informe(iactual, todos);
            todos.get(iactual).pagaImpuesto(importe);
        }
    }
    
    private void recibeJugador_juez(int iactual, ArrayList<Jugador> todos){
        if(juagadorCorrecto(iactual, todos)){
            informe(iactual, todos);
            todos.get(iactual).encarcelar(carcel);
        }
    }
    
    private void recibeJugador_sorpresa(int iactual, ArrayList<Jugador> todos){
        sorpresa = mazo.siguiente();
        informe(iactual, todos);
        sorpresa.aplicarAJugador(iactual, todos);
    }

    @Override
    public String toString() {
        return "Casilla{" + "nombre=" + nombre + ", importe=" + importe + ", tipo=" + tipo + ", tituloPropiedad=" + tituloPropiedad + ", sorpresa=" + sorpresa + ", mazo=" + mazo + '}';
    }
    
}
