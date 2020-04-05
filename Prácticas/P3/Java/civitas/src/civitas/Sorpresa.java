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
public class Sorpresa {
    private String texto;
    private int valor;
    private MazoSorpresas mazo;
    private TipoSorpresa tipo;
    private Tablero tablero;
    private Diario diario = Diario.getInstance();
    
    Sorpresa(TipoSorpresa tipo, Tablero tablero){
        init();
        this.tipo = tipo;
        this.tablero = tablero;
        this.texto = "Carcel";
    }
    
    Sorpresa(TipoSorpresa tipo, Tablero tablero, int valor, String texto){
        init();
        this.tipo = tipo;
        this.tablero = tablero;
        this.valor = valor;
        this.texto = texto;
    }
    
    Sorpresa(TipoSorpresa tipo, int valor, String texto){
        init();
        this.tipo = tipo;
        this.valor = valor;
        this.texto = texto;
    }
    
    Sorpresa(TipoSorpresa tipo, MazoSorpresas mazo){
        init();
        this.tipo = tipo;
        this.mazo = mazo;
        this.texto = "Evita la carcel";
    }
    
    void aplicarAJugador(int actual, ArrayList<Jugador> todos){
        switch(tipo){
            case IRCASILLA:
                aplicarAJugador_irACasilla(actual, todos);
                break;
            case IRCARCEL:
                aplicarAJugador_irCarcel(actual, todos);
                break;
            case PAGARCOBRAR:
                aplicarAJugador_pagarCobrar(actual, todos);
                break;  
            case PORCASAHOTEL:
                aplicarAJugador_porCasaHotel(actual, todos);
                break;  
            case PORJUGADOR:
                aplicarAJugador_porJugador(actual, todos);
                break;    
            case SALIRCARCEL:
                aplicarAJugador_salirCarcel(actual, todos);
                break;
        }
    }
    
    private void aplicarAJugador_irACasilla(int actual, ArrayList<Jugador> todos){
        if(juagadorCorrecto(actual, todos)){
            informe(actual, todos);
            int casillaActual = todos.get(actual).getNumCasillaActual();
            int tirada = tablero.calcularTirada(casillaActual, valor);
            int nuevaPos = tablero.nuevaPosicion(casillaActual, tirada);
            todos.get(actual).moverACasilla(nuevaPos);
            tablero.getCasilla(nuevaPos).recibeJugador(actual, todos);
        }
    }
    
    private void aplicarAJugador_irCarcel(int actual, ArrayList<Jugador> todos){
        if(juagadorCorrecto(actual, todos)){
            informe(actual, todos);
            todos.get(actual).encarcelar(tablero.getCarcel());
        }
    }
    
    private void aplicarAJugador_pagarCobrar(int actual, ArrayList<Jugador> todos){
        if(juagadorCorrecto(actual, todos)){
            informe(actual, todos);
            todos.get(actual).modificarSaldo(valor);
        }
    }
    
    private void aplicarAJugador_porCasaHotel(int actual, ArrayList<Jugador> todos){
        if(juagadorCorrecto(actual, todos)){
            informe(actual, todos);
            todos.get(actual).modificarSaldo(valor*todos.get(actual).cantidadCasasHoteles());
        }
    }
    
    private void aplicarAJugador_porJugador(int actual, ArrayList<Jugador> todos){
        if(juagadorCorrecto(actual, todos)){
            informe(actual, todos);
            Sorpresa aux = new Sorpresa(TipoSorpresa.PAGARCOBRAR, valor*-1, "aux");
            Sorpresa aux2 = new Sorpresa(TipoSorpresa.PAGARCOBRAR, valor*(todos.size()-1), "aux2");
            for(int i=0; i<todos.size(); i++){
                if(i != actual){
                    aux.aplicarAJugador(i, todos);
                }
                else
                    aux2.aplicarAJugador(actual, todos);
            }
        }
    }
    
    private void aplicarAJugador_salirCarcel(int actual, ArrayList<Jugador> todos){
        boolean puede = false;
        if(juagadorCorrecto(actual, todos)){
            informe(actual, todos);
            for(Jugador j:todos){
                if(j.tieneSalvoconducto() == true && j != todos.get(actual))
                    puede = true;
            }
            if(!puede){
                todos.get(actual).obtenerSalvoconducto(this);
                salirDelMazo();
            }
        }
    }
    
    private void informe(int actual, ArrayList<Jugador> todos){
        diario.ocurreEvento("Se aplica la sorpresa " + texto + " a " + todos.get(actual).getNombre());
    }
    
    private void init(){
        this.valor = -1;
        this.mazo = new MazoSorpresas();
        this.tablero = new Tablero(10);
        this.texto = "";
    }
    
    public boolean juagadorCorrecto(int actual, ArrayList<Jugador> todos){
        return todos.size() > actual;
    }
    
    void salirDelMazo(){
        if(tipo == TipoSorpresa.SALIRCARCEL)
            mazo.inhabilitarCartaEspecial(this);
    }
    
    void usadas(){
        if(tipo == TipoSorpresa.SALIRCARCEL)
            mazo.habilitarCartaEspecial(this);
    }

    @Override
    public String toString() {
        return "Sorpresa{" + "texto=" + texto + ", valor=" + valor + ", mazo=" + mazo + ", tipo=" + tipo + ", tablero=" + tablero + '}';
    }
}
