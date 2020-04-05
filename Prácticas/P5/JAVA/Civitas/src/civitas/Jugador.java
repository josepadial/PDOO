/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

import java.util.ArrayList;
import GUI.Dado;

/**
 *
 * @author Jose Padial
 */
public abstract class Jugador implements Comparable<Jugador>{
    protected static final int casasMax = 4;
    protected static final int casasPorHotel = 4;
    protected  boolean encarcelado;
    protected static final int hotelesMax = 4;
    private String nombre;
    private int numCasillaActual;
    protected static final float pasoPorSalida = 1000;
    protected static final float precioLibertad = 200;
    private boolean puedeComprar;
    private float saldo;
    private static final float saldoInicial = 7500;
    Diario diario = Diario.getInstance();
    SorpresaSalirCarcel cartaSalirCarcel;
    ArrayList<TituloPropiedad> propiedades;
    
    Jugador(String nom){
        nombre = nom;
        saldo = saldoInicial;
        encarcelado = false;
        numCasillaActual = 0;
        propiedades = new ArrayList<>();
        puedeComprar = true;
    }
    
    protected Jugador(Jugador otro){
        this.nombre = otro.getNombre();
        this.numCasillaActual = otro.getNumCasillaActual();
        this.saldo = otro.getSaldo();
        this.propiedades = otro.getPropiedades();
        this.encarcelado = otro.isEncarcelado();
        this.puedeComprar = otro.getPuedeComprar();
    }
    
    protected JugadorEspeculador convertirme(int fianza){
        JugadorEspeculador especulador = new JugadorEspeculador(this,fianza);
        for (TituloPropiedad t:propiedades){
            t.actualizarPropietarioPorConversion(especulador);
        }
        return especulador;
    }
    
    boolean cancelarHipoteca(int ip){
        boolean resultado = false;
        
        if(encarcelado == true){
            return resultado;
        }
        else{
            if(existeLaPropiedad(ip)==true){
                TituloPropiedad propiedad = propiedades.get(ip);
                float cantidad = propiedad.getImporteCancelarHipoteca();
                
                if(puedoGastar(cantidad) == true){
                    resultado = propiedad.cancelarHipoteca(this);
                }
                
                if(resultado ==true){
                    diario.ocurreEvento("El jugador "+nombre+ " cancela la hipoteca de la propiedad "+ip);
                }
            }
            return resultado;
        }
    }
    
    int cantidadCasasHoteles(){
        int numCasas = 0, numHoteles = 0;
        
        for(int i = 0; i < propiedades.size(); i++){
            numCasas = numCasas + propiedades.get(i).getNumCasas();
            numHoteles = numHoteles + propiedades.get(i).getNumHoteles();
        }
        
        return numCasas + numHoteles;
    }
    
    public int compareTo(Jugador otro){
        int otroCapital = (int) otro.getSaldo();
        return (int) ((int) otroCapital - this.getSaldo());
    }
    
    boolean comprar(TituloPropiedad titulo){
        boolean resultado = false;
        
        if(encarcelado == true){
            return resultado;
        }
        
        if(puedeComprar == true){
            float cantidad = titulo.getPrecioCompra();
            if(puedoGastar(cantidad) == true){
                resultado = titulo.comprar(this);
                
                if(resultado == true){
                   propiedades.add(titulo);
                   diario.ocurreEvento("El jugador "+nombre+ " compra la propiedad "+titulo.toString());
                }
                
                puedeComprar = false;
            }
            
        }
        return resultado;
    }
    
    boolean construirCasa(int ip){
        boolean resultado = false;
        
        if(encarcelado == true){
            return resultado;
        }
        
        if(existeLaPropiedad(ip) == true){
            TituloPropiedad propiedad = propiedades.get(ip);
            boolean puedoEdificarCasa = puedoEdificarCasa(propiedad);
            boolean puede = false;
            float precio = propiedad.getPrecioEdificar();
            
            if(puedoGastar(precio) == true && puedoEdificarCasa == true){
                puede = true;
            }
            
            if(puede == true){
                resultado = propiedad.construirCasa(this);
            }
            
            diario.ocurreEvento("El jugador "+nombre+ " construye casa en la propiedad "+ip);
            
        }
        
        return resultado;
        
    }
    
    boolean construirHotel(int ip){
        boolean resultado = false;
        
        if(encarcelado == true){
            return resultado;
        }
        
        if(existeLaPropiedad(ip) == true){
            TituloPropiedad propiedad = propiedades.get(ip);
            boolean puedoEdificarHotel = puedoEdificarHotel(propiedad);
            boolean puede = false;
            float precio = propiedad.getPrecioEdificar();
            
            if(puedoGastar(precio) == true && puedoEdificarHotel == true){
                puede = true;
            }
            
            if(puede == true){
                resultado = propiedad.construirHotel(this);
                propiedad.derruirCasas(casasPorHotel,this);
            }
            
            diario.ocurreEvento("El jugador "+nombre+ " construye hotel en la propiedad "+ip);
        }
        
        
        return resultado;
    }
    
    protected boolean debeSerEncarcelado(){
        boolean debe = false;
        if(encarcelado == true){
            debe = false;
        }
        else{
            if(tieneSalvoConducto() == false){
                debe = true;
            }
            else{
                perderSalvoConducto();
                diario.ocurreEvento("El jugador se libera de la carcel");
                encarcelado = false;
            }
        }
        
        return debe;
    }
    
    boolean enBancarrota(){
        if(saldo < 0){
            return true;
        }
        else{
            return false;
        }
    }
    
    boolean encarcelar(int numCasillaCarcel){
        if(debeSerEncarcelado() == true){
            moverACasilla(numCasillaCarcel);
            encarcelado = true;
            diario.ocurreEvento("Jugador " + nombre + " encarcelado");
            return encarcelado;
        }
        else{
            return encarcelado;
        }
        
    }
    
    private boolean existeLaPropiedad(int ip){
        boolean existe = false;
        
        for(int i = 0; i < propiedades.size(); i++){
            if(propiedades.get(i).getPropietario() == this){
                existe = true;
            }
            
        }
        
        return existe;
    }
    
    private int getCasasMax(){
        return casasMax;
    }
    
    int getCasasPorHotel(){
        return casasPorHotel;
    }
    
    private int getHotelesMax(){
        return hotelesMax;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    int getNumCasillaActual(){
        return numCasillaActual;
    }
    
    private float getPrecioLibertad(){
        return precioLibertad;
    }
    
    private float getPremioPasoSalida(){
        return pasoPorSalida;
    }
    
    public ArrayList<TituloPropiedad> getPropiedades(){
        return propiedades;
    }
    
    boolean getPuedeComprar(){
        return puedeComprar;
    }
    
    public float getSaldo(){
        return saldo;
    }
    
    boolean hipotecar(int ip){
        boolean resultado = false;
        
        if(encarcelado == true){
            return resultado;
        }
        
        if(existeLaPropiedad(ip)==true){
            TituloPropiedad propiedad = propiedades.get(ip);
            resultado = propiedad.hipotecar(this);
        }
        
        if(resultado == true){
            diario.ocurreEvento("El jugador "+nombre+ " hipoteca la propiedad "+ip);
        }
        
        return resultado;
        
    }
    
    public boolean getEspeculador(){
        return false;
    }
    
    public boolean isEncarcelado(){
        if(encarcelado == true){
            return true;
        }
        else{
            return false;
        }
    }
    
    boolean modificarSaldo(float cantidad){
        saldo = saldo + cantidad;
        diario.ocurreEvento("Modificado el saldo del jugador " + nombre + " saldo actual " + saldo + " ");
        return true;
    }
    
    boolean moverACasilla(int numCasilla){
        if(encarcelado == true){
            return false;
        }
        else{
            numCasillaActual = numCasilla;
            puedeComprar = false;
            diario.ocurreEvento("Se ha movido al jugador " + nombre + " hacia la casilla " + numCasillaActual + " ");
            return true;
        }
    }
    
    boolean obtenerSalvoconducto(SorpresaSalirCarcel sorpresa){
        if(encarcelado == true){
            return false;
        }
        else{
            cartaSalirCarcel = sorpresa;
            return true;
        }
    }
    
    boolean paga(float cantidad){
        return modificarSaldo(cantidad * -1);
    }
    
    boolean pagaAlquiler(float cantidad){
        if(encarcelado == true){
            return false;
        }
        else{
            return paga(cantidad);
        }
    }
    
    boolean pagaImpuesto(float cantidad){
        if(encarcelado == true){
            return false;
        }
        else{
            return paga(cantidad);
        }
    }
    
    boolean pasaPorSalida(){
        modificarSaldo(pasoPorSalida);
        diario.ocurreEvento("El jugador " + nombre + " ha pasado por la casilla salida ");
        return true;
    }
    
    private void perderSalvoConducto(){
        cartaSalirCarcel.usada();
        cartaSalirCarcel = null;
    }
    
    boolean puedeComprarCasilla(){
        if(encarcelado == true){
            puedeComprar = false;
            return puedeComprar;
        }
        else{
            puedeComprar = true;
            return puedeComprar;
        }
        
    }
    
    private boolean puedeSalirCarcelPagando(){
        if(saldo >= precioLibertad){
            return true;
        }
        else{
            return false;
        }
    }
    
    private boolean puedoEdificarCasa(TituloPropiedad propiedad){
        boolean puede = false;
        
        for(int i = 0; i < propiedades.size(); i++){
            if(propiedad == propiedades.get(i) && propiedades.get(i).getNumCasas() < casasMax){
                puede = true;
            }
        }
        
        return puede;
    }
    
    private boolean puedoEdificarHotel(TituloPropiedad propiedad){
        boolean puede = false;
        
        for(int i = 0; i < propiedades.size(); i++){
            if(propiedad == propiedades.get(i) && propiedades.get(i).getNumHoteles() < hotelesMax){
                if(propiedad.getNumCasas()>=getCasasPorHotel()){
                puede = true;
                }
            }
        }
        
        return puede;
    }
    
    private boolean puedoGastar(float precio){
        if(encarcelado == true){
            return false;
        }
        else if(precio > saldo){
            return false;
        }
        else{
            return true;
        }
    }
    
    boolean recibe(float cantidad){
        if(encarcelado == true){
            return false;
        }
        else{
            return modificarSaldo(cantidad);
        }
    }
    
    boolean salirCarcelPagando(){
        if(encarcelado == true && puedeSalirCarcelPagando() == true){
            paga(precioLibertad);
            encarcelado = false;
            diario.ocurreEvento("El jugador " + nombre + " paga 200 por salir de la carcel ");
            return true;
        }
        else{
            return false;
        }
    }
    
    boolean salirCarcelTirando(){
        Dado dado = Dado.getInstance();
        boolean puede = dado.salgoDeLaCarcel();
        
        if(puede == true){
          diario.ocurreEvento("El jugador " + nombre + " ha tenido suerte y sale de la carcel tirando el dado ");
          encarcelado = false;
        }
        
        return puede;
    }
    
    boolean getEncarcelado(){
        return encarcelado;
    }
    
    boolean tieneAlgoQueGestionar(){
        if(propiedades.isEmpty()){
            return false;
        }
        else{
            return true;
        }
    }
    
    boolean tieneSalvoConducto(){
        if(cartaSalirCarcel != null){
            return true;
        }
        else{
            return false;
        }
    }
    
    boolean vender(int ip){
        if(encarcelado == true){
            return false;
        }
        else if(existeLaPropiedad(ip) == true){
           propiedades.get(ip).vender(this);
           propiedades.remove(ip);
           diario.ocurreEvento("Se ha vendido la propiedad " + ip + " ");
           return true;
        }
        else{
            return false;
        }
    }
    
    int getNumCasasJugador(){
        
        int numeroCasas = 0;
        
        for(int i = 0; i < propiedades.size(); i++){
            numeroCasas = propiedades.get(i).getNumCasas();
        }
        
        return numeroCasas;
    }
    
    int getNumHotelesJugador(){
        
        int numeroHoteles = 0;
        
        for(int i = 0; i < propiedades.size(); i++){
            numeroHoteles = propiedades.get(i).getNumHoteles();
        }
        
        return numeroHoteles;
    }
    
    public ArrayList<String> cadenaPropiedades(){
        ArrayList<String> cadenas = new ArrayList<>();
       
        if(propiedades.size() != 0){
            for(TituloPropiedad i: propiedades){
                cadenas.add(i.getNombre());
            }
        }
        
        return cadenas;
    }
    
    public String toString() {
        return "Jugador{" + "nombre=" + nombre + ", encarcelado=" + encarcelado + ", numCasillaActual=" + numCasillaActual + ", saldo=" + saldo + ", cartaSalirCarcel=" + cartaSalirCarcel + ", propiedades=" + propiedades + "}";
    }
    
    
    
}
