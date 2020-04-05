/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;
import java.util.*;


/**
 *
 * @author Jose Padial
 */
public class CivitasJuego {
    
    private int indiceJugadorActual;
    Dado dado = Dado.getInstance();
    OperacionesJuego operacion;
    MazoSorpresas mazo;
    Tablero tablero;
    ArrayList<Jugador> jugadores = new ArrayList<>();
    EstadosJuego estado;
    GestorEstados gestorEstados;
    public Diario diario = Diario.getInstance();
    
 
    CivitasJuego(ArrayList<String> nombres){
          
        for (String i :nombres){
            jugadores.add(new Jugador(i) {});
        }
        
        gestorEstados = new GestorEstados();
        estado = gestorEstados.estadoInicial();
        
        indiceJugadorActual = 0; // Debo implementar un generador de numeros aleatorios
        
        mazo = new MazoSorpresas();
        
        inicializarTablero(mazo);
        inicializarMazoSorpresas(tablero);
    }
    
    private void avanzaJugador(){
        Jugador jugadorActual = jugadores.get(indiceJugadorActual);
        int posAct = jugadorActual.getNumCasillaActual();
        int tirada = dado.tirar();
        int posNew = tablero.nuevaPosicion(posAct, tirada);
        Casilla casillaActual = tablero.getCasilla(posNew);
        contabilizarPasosPorSalida(jugadorActual);
        jugadorActual.moverACasilla(posNew);
        casillaActual.recibeJugador(indiceJugadorActual, jugadores);
        contabilizarPasosPorSalida(jugadorActual);
    }
    
    public boolean cancelarHipoteca(int ip){
        return jugadores.get(indiceJugadorActual).cancelarHipoteca(ip);
    }
    
    public boolean comprar(){
        int numCas = jugadores.get(indiceJugadorActual).getNumCasillaActual();
        TituloPropiedad propiedad = ((CasillaCalle)tablero.getCasilla(numCas)).getTituloPropiedad();
                
        return jugadores.get(indiceJugadorActual).comprar(propiedad);
    }
    
  public boolean construirCasa(int ip){
        return jugadores.get(indiceJugadorActual).construirCasa(ip);
    }
    
    public boolean construirHotel(int ip){
        return jugadores.get(indiceJugadorActual).construirHotel(ip);
    }
    
    private void contabilizarPasosPorSalida(Jugador jugadorActual){
        if (tablero.getPorSalida() > 0){
            jugadorActual.pasaPorSalida();
        }
    }
    
    public boolean finalDelJuego(){
        boolean bancarrota = false;
        
        for (Jugador i :jugadores){
            if(i.enBancarrota() == true){
                bancarrota = true;
            }
        }
        
        return bancarrota;
    }
    
    public Casilla getCasillaActual(){
        int numCas = jugadores.get(indiceJugadorActual).getNumCasillaActual();
        return tablero.getCasilla(numCas);
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
        mazo.alMazo(new SorpresaIrCarcel(tablero));
        mazo.alMazo(new SorpresaIrCasilla(tablero,10,"Desplazate hacia la casilla indicada"));
        mazo.alMazo(new SorpresaIrCasilla(tablero,16,"Desplazate hacia la casilla indicada"));
        mazo.alMazo(new SorpresaIrCasilla(tablero,tablero.getCarcel(),"A la carcel"));
        mazo.alMazo(new SorpresaPagarCobrar(tablero,1000,"Recibes la cantidad indicada"));
        mazo.alMazo(new SorpresaPagarCobrar(tablero,-1000,"Pierdes la cantidad indicada"));
        mazo.alMazo(new SorpresaPorCasaHotel(tablero,-50,"Debes pagar los impuestos de tus propiedades"));
        mazo.alMazo(new SorpresaPorCasaHotel(tablero,50,"Hacienda te ha hecho un regalito"));
        mazo.alMazo(new SorpresaPorJugador(tablero,-100,"El jugador debe pagar la cantidad indicada a cada jugador"));
        mazo.alMazo(new SorpresaPorJugador(tablero,100,"El jugador recibe la cantidad indicada de cada jugador"));
        mazo.alMazo(new SorpresaSalirCarcel(mazo));
        
    }
    
    private void inicializarTablero(MazoSorpresas mazo){
        tablero = new Tablero(8);
        tablero.aniadeCasilla(new CasillaCalle(new TituloPropiedad("Iglesia", 100, (float)19.0, 300, 150, 300)));
        tablero.aniadeCasilla(new CasillaCalle(new TituloPropiedad("C/ Istán", 75, (float)19.0, 100, 100, 500)));
        tablero.aniadeCasilla(new CasillaCalle(new TituloPropiedad("C/ Don Vito", 125, (float)19.0, 200, 200, 350)));
        tablero.aniadeCasilla(new CasillaSorpresa("SORPRESA",mazo));
        tablero.aniadeCasilla(new CasillaCalle(new TituloPropiedad("C/ Pepe Osorio", 50, (float)19.0, 100, 300, 350)));
        tablero.aniadeCasilla(new CasillaCalle(new TituloPropiedad("C/ Pajaritos", 150, (float)19.0, 300, 500, 300)));
        tablero.aniadeJuez();
        tablero.aniadeCasilla(new CasillaSorpresa("SORPRESA",mazo));
        tablero.aniadeCasilla(new CasillaCalle(new TituloPropiedad("C/ Lagasca", 200, (float)19.0, 175, 120, 450)));
        tablero.aniadeCasilla(new CasillaCalle(new TituloPropiedad("C/ San Miguel", 75, (float)19.0, 225, 150, 225)));
        tablero.aniadeCasilla(new CasillaImpuesto("IMPUESTO",75));
        tablero.aniadeCasilla(new CasillaCalle(new TituloPropiedad("C/ Pizarro", 215, (float)19.0, 420, 350, 200)));
        tablero.aniadeCasilla(new CasillaSorpresa("SORPRESA",mazo));
        tablero.aniadeCasilla(new CasillaCalle(new TituloPropiedad("C/ Oriental", 140, (float)19.0, 330, 500, 380)));
        tablero.aniadeCasilla(new Casilla("PARKING"));
        tablero.aniadeCasilla(new CasillaCalle(new TituloPropiedad("C/ Dolores", 200, (float)19.0, 400, 550, 325)));
        tablero.aniadeCasilla(new CasillaCalle(new TituloPropiedad("C/ Caballero", 300, (float)19.0, 400, 700, 500)));
        tablero.aniadeCasilla(new CasillaCalle(new TituloPropiedad("C/ Ruiz", 300, (float)19.0, 500, 800, 550)));
        
    }
    
    public void pasarTurno(){
        indiceJugadorActual = (indiceJugadorActual + 1) % jugadores.size();
    }
    
    public ArrayList<Jugador> ranking(){
        ArrayList<Jugador> ranking;
        ranking = jugadores;
        Collections.sort(ranking);
        return ranking;
    }
    
    public boolean salirCarcelPagando(){
        return jugadores.get(indiceJugadorActual).salirCarcelPagando();
    }
    
    public boolean salirCarcelTirando(){
        return jugadores.get(indiceJugadorActual).salirCarcelTirando();
    }
    
    public OperacionesJuego siguientePaso(){
        Jugador jugadorActual = jugadores.get(indiceJugadorActual);
        operacion = gestorEstados.operacionesPermitidas(jugadorActual,estado);
        
        if(operacion == OperacionesJuego.PASAR_TURNO){
            pasarTurno();
            siguientePasoCompletado(operacion);
        }
        else if(operacion == OperacionesJuego.AVANZAR){
            avanzaJugador();
            siguientePasoCompletado(operacion);
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
