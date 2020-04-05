/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Jose Padial
 */
public class CivitasJuego {
    private int indiceJugadorActual;
    private Tablero tablero;
    private MazoSorpresas mazo;
    private ArrayList<Jugador> jugadores = new ArrayList();
    private EstadosJuego estado;
    private GestorEstados gestorEstados;
    private Dado dado = Dado.getInstance();
    
    public CivitasJuego(ArrayList<String> nombres){
        for(String n:nombres){
            jugadores.add(new Jugador(n));
        }
        gestorEstados = new GestorEstados();
        estado = gestorEstados.estadoInicial();
        indiceJugadorActual = dado.quienEmpieza(jugadores.size());
        mazo = new MazoSorpresas();
        inicializarTablero(mazo);
        inicializarMazoSorpresas(tablero);
    }
    
    private void avanzaJugador(){
        Jugador jugadorActual = getJugadorActual();
        int posicionActual = jugadorActual.getNumCasillaActual();
        int tirada = dado.tirar();
        int posicionNueva = tablero.nuevaPosicion(posicionActual, tirada);
        Casilla casilla = tablero.getCasilla(posicionNueva);
        contabilizarPasosPorSalida(jugadorActual);
        jugadorActual.moverACasilla(posicionNueva);
        casilla.recibeJugador(indiceJugadorActual, jugadores);
        contabilizarPasosPorSalida(jugadorActual);
    }
    
    public boolean cancelarHipoteca(int ip){
        return jugadores.get(indiceJugadorActual).cancelarHipoteca(ip);
    }
    
    public boolean comprar(){
        Jugador jugadorActual = getJugadorActual();
        int numCasillaActual = jugadorActual.getNumCasillaActual();
        Casilla casilla = tablero.getCasilla(numCasillaActual);
        TituloPropiedad titulo = casilla.getTituloPropiedad();
        return jugadorActual.comprar(titulo);
    }
    
    public boolean construirCasa(int ip){
        return jugadores.get(indiceJugadorActual).construirCasa(ip);
    }
    
    public boolean construirHotel(int ip){
        return jugadores.get(indiceJugadorActual).construirHotel(ip);
    }
    
    private void contabilizarPasosPorSalida(Jugador juagadorActual){
        int aux = tablero.getPorSalida();
        for(int i=0; i<aux; i++)
            juagadorActual.pasaPorSalida();            
    }
    
    public boolean finalDelJuego(){
        for(Jugador j:jugadores){
            if(j.enBancarrota())
                return true;
        }
        return false;
    }
    
    public Casilla getCasillaActual(){
        return tablero.getCasilla(getJugadorActual().getNumCasillaActual());
    }
    
    public Jugador getJugadorActual(){
        return jugadores.get(indiceJugadorActual);
    }
    
    public boolean hipotecar(int ip){
        return jugadores.get(indiceJugadorActual).hipotecar(ip);
    }
    
    public String infoJugadorTexto(){
        return jugadores.get(indiceJugadorActual).toString();
    }
    
    private void inicializarMazoSorpresas(Tablero tablero){
        mazo.alMazo(new Sorpresa(TipoSorpresa.PAGARCOBRAR, 1000, "Vas por la calle y te encuentras 1000€ ¡ESTÁS DE SUERTE!"));
        mazo.alMazo(new Sorpresa(TipoSorpresa.PAGARCOBRAR, -1000, "¡OH NO! Has perdido 1000€ Este año no podrás pagar Mordor"));
        mazo.alMazo(new Sorpresa(TipoSorpresa.IRCASILLA, tablero, tablero.getCarcel(), "¡Ya esta bien! Ve a la cárcel directamente"));
        mazo.alMazo(new Sorpresa(TipoSorpresa.IRCASILLA, tablero, 15, "El juez te raclama"));
        mazo.alMazo(new Sorpresa(TipoSorpresa.IRCASILLA, tablero, 5, "Ve corriendo al parking, te esperamos"));
        mazo.alMazo(new Sorpresa(TipoSorpresa.PORCASAHOTEL, 400, "Cobra 400€ por cada casa o hotel que poseas"));
        mazo.alMazo(new Sorpresa(TipoSorpresa.PORCASAHOTEL, -400, "Paga 400€ por cada casa o cada hotel que poseas"));
        mazo.alMazo(new Sorpresa(TipoSorpresa.PORJUGADOR, 450, "Recibes 450€ de cada jugador"));
        mazo.alMazo(new Sorpresa(TipoSorpresa.PORJUGADOR, -300, "Paga 300€ a cada jugador"));
        mazo.alMazo(new Sorpresa(TipoSorpresa.SALIRCARCEL, mazo));
        mazo.alMazo(new Sorpresa(TipoSorpresa.IRCARCEL, tablero));
    }
    
    private void inicializarTablero(MazoSorpresas mazo){
        tablero = new Tablero(10);
        tablero.añadeCasilla(new Casilla("Salida"));
        tablero.añadeCasilla(new Casilla(new TituloPropiedad("C/Recogidas Nº1", 650, 70, 10, 650, 450)));
        tablero.añadeCasilla(new Casilla(mazo, "Sorpresa1"));
        tablero.añadeCasilla(new Casilla(new TituloPropiedad("C/Recogidas Nº2", 750, 80, 15, 700, 500)));
        tablero.añadeCasilla(new Casilla(new TituloPropiedad("C/Recogidas Nº3", 550, 60, -10, 600, 350)));
        tablero.añadeCasilla(new Casilla("Parking"));
        tablero.añadeCasilla(new Casilla(new TituloPropiedad("C/San Antón Nº1", 500, 50, -10, 150, 250)));
        tablero.añadeCasilla(new Casilla(350, "Impuesto"));
        tablero.añadeCasilla(new Casilla(new TituloPropiedad("C/San Antón Nº2", 600, 70, 10, 300, 350)));
        tablero.añadeCasilla(new Casilla("Cárcel"));
        tablero.añadeCasilla(new Casilla(new TituloPropiedad("C/San Antón Nº3", 550, 60, -10, 250, 300)));
        tablero.añadeCasilla(new Casilla(new TituloPropiedad("Paseo del Violón Nº1", 500, 50, -20, 200, 250)));
        tablero.añadeCasilla(new Casilla(mazo, "Sorpresa2"));
        tablero.añadeCasilla(new Casilla(new TituloPropiedad("Paseo del Violón Nº2", 800, 70, 10, 500, 400)));
        tablero.añadeCasilla(new Casilla(new TituloPropiedad("Paseo del Violón Nº3", 600, 60, -15, 350, 350)));
        tablero.añadeJuez();
        tablero.añadeCasilla(new Casilla(mazo, "Sorpresa3"));
        tablero.añadeCasilla(new Casilla(new TituloPropiedad("Cmno de Ronda Nº1", 950, 90, 15, 900, 650)));
        tablero.añadeCasilla(new Casilla(new TituloPropiedad("Cmno de Ronda Nº2", 850, 80, 10, 800, 600)));
        tablero.añadeCasilla(new Casilla(new TituloPropiedad("Cmno de Ronda Nº3", 1000, 100, 20, 950, 750)));
    }
    
    private void pasarTurno(){
        indiceJugadorActual = (indiceJugadorActual+1)%jugadores.size();
    }
    
    public ArrayList<Jugador> ranking(){
        ArrayList<Jugador> rank;
        Collections.sort(jugadores);
        rank = jugadores;
        return rank;
    }
    
    public boolean salirCarcelPagando(){
        return jugadores.get(indiceJugadorActual).salirCarcelPagando();
    }
    
    public boolean salirCarcelTirando(){
        return jugadores.get(indiceJugadorActual).salirCarcelTirando();
    }
    
    public OperacionesJuego siguientePaso(){
        Jugador jugadorActual = jugadores.get(indiceJugadorActual);
        OperacionesJuego operacion = gestorEstados.operacionesPermitidas(jugadorActual, estado);
        switch(operacion){
            case PASAR_TURNO:
                pasarTurno();
                siguientePasoCompletado(operacion);
                break;
            case AVANZAR:
                avanzaJugador();
                siguientePasoCompletado(operacion);
                break;
        }
        return operacion;
    }
    
    public void siguientePasoCompletado(OperacionesJuego operacion){
        estado = gestorEstados.siguienteEstado(jugadores.get(indiceJugadorActual), estado, operacion);
    }
    
    public boolean vender(int ip){
        return jugadores.get(indiceJugadorActual).vender(ip);
    }
}
