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
public class Jugador implements Comparable<Jugador>{
    private ArrayList<TituloPropiedad> propiedades;
    private Sorpresa salvoconducto;
    private Diario diario = Diario.getInstance();
    private Dado dado = Dado.getInstance();
    protected static int casasMax = 4;
    protected static int casasPorHotel = 4;
    protected boolean encarcelado;
    protected static int hotelesMax = 4;
    private String nombre;
    private int numCasillaActual;
    protected static float pasoPorSalida = 1000;
    protected static float precioLibertad = 200;
    private boolean puedeComprar;
    private float saldo;
    private static float saldoInicial = 7500;
    
    Jugador(String nombre){
        this.nombre = nombre;
        this.encarcelado = false;
        this.propiedades = new ArrayList();
        this.saldo = saldoInicial;
        this.puedeComprar = false;
    }
    
    protected Jugador(Jugador otro){
        this.encarcelado = otro.isEncarcelado();
        this.nombre = otro.getNombre();
        this.saldo = otro.getSaldo();
        this.propiedades = otro.getPropiedades();
        this.numCasillaActual = otro.getNumCasillaActual();
        this.puedeComprar = otro.getPuedeComprar();
    }
    
    boolean cancelarHipoteca(int ip){
        return true;
    }
    
    int cantidadCasasHoteles(){
        int suma = 0;
        for(TituloPropiedad p : propiedades){
            suma += p.getNumCasas() + p.getNumHoteles();
        }
        return suma;
    }
    
    @Override
    public int compareTo(Jugador otro){
        int otroCapital = (int) otro.getSaldo();
        return (int) ((int) otroCapital - this.getSaldo());
    }
    
    boolean comprar(TituloPropiedad titulo){
       return true; 
    }
    
    boolean construirCasa(int ip){
        return true;
    }
    
    boolean construirHotel(int ip){
        return true;
    }
    
    protected boolean debeSerEncarcelado(){
        if(encarcelado == true)
            return false;
        else{
            if(!tieneSalvoconducto())
                return true;
            else{
                perderSalvoConducto();
                diario.ocurreEvento("Se libra de la carcel el jugador: " + nombre);
                return false;
            }
        }
    }
    
    boolean enBancarrota(){
        return saldo <= 0;
    }
    
    boolean encarcelar(int numCasillaCarcel){
        if(debeSerEncarcelado()){
            moverACasilla(numCasillaCarcel);
            encarcelado = true;
            diario.ocurreEvento("Se encarcela al jugador:  " + nombre);
        }
        return encarcelado;
    }
    
    boolean existeLaPropiedad(int ip){
       return propiedades.contains(propiedades.get(ip));
    }

    protected ArrayList<TituloPropiedad> getPropiedades() {
        return propiedades;
    }

    private int getCasasMax() {
        return casasMax;
    }

    int getCasasPorHotel() {
        return casasPorHotel;
    }

    public boolean isEncarcelado() {
        return encarcelado;
    }

    private int getHotelesMax() {
        return hotelesMax;
    }

    protected String getNombre() {
        return nombre;
    }

    int getNumCasillaActual() {
        return numCasillaActual;
    }

    private float getPrecioLibertad() {
        return precioLibertad;
    }

    boolean getPuedeComprar() {
        return puedeComprar;
    }

    protected float getSaldo() {
        return saldo;
    }
    
    private float getPremioPasoSalida(){
        return pasoPorSalida;
    }
    
    boolean hipotecar(int ip){
        return true;
    }
    
    boolean modificarSaldo(float cantidad){
        saldo += cantidad;
        diario.ocurreEvento("Se ha modificado el saldo en: " + cantidad);
        return true;
    }
    
    boolean moverACasilla(int numCasilla){
        if(encarcelado == true)
            return false;
        else{
            numCasillaActual = numCasilla;
            puedeComprar = false;
            diario.ocurreEvento("Jugador movido a la casilla: " + numCasilla);
            return true;
        }
    }
    
    boolean obtenerSalvoconducto(Sorpresa sorpresa){
        if(encarcelado == true)
            return false;
        else{
            salvoconducto = sorpresa;
            return true;
        }
    }
    
    boolean paga(float cantidad){
        return modificarSaldo(cantidad*-1);
    }
    
    boolean pagaAlquiler(float cantidad){
        if(encarcelado == true)
            return false;
        else
            return paga(cantidad);
    }
    
    boolean pagaImpuesto(float cantidad){
        if(encarcelado == true)
            return false;
        else
            return paga(cantidad);
    }
    
    boolean pasaPorSalida(){
        modificarSaldo(pasoPorSalida);
        diario.ocurreEvento("Jugador: " + nombre + " pasa por salida");
        return true;
    }
    
    private void perderSalvoConducto(){
        salvoconducto.usadas();
        salvoconducto = null;
    }
    
    boolean puedeComprarCasilla(){
        if(encarcelado == true)
            puedeComprar = false;
        else
            puedeComprar = true;
        return puedeComprar;
    }
    
    private boolean puedeSalirCarcelPagando(){
        return saldo >= precioLibertad;
    }
    
    private boolean puedoEdificarCasa(TituloPropiedad propiedad){
        if(puedoGastar(propiedad.getPrecioEdificar()) && propiedad.getNumCasas() < 4)
            return true;
        return false;
    }
    
    private boolean puedoEdificarHotel(TituloPropiedad propiedad){
        if(puedoGastar(propiedad.getPrecioEdificar()) && propiedad.getNumHoteles()< 4 && propiedad.getNumCasas() == 4)
            return true;
        return false;
    }
    
    private boolean puedoGastar(float precio){
        if(encarcelado == true)
            return false;
        else
            return saldo >= precio;
    }
    
    boolean recibe(float cantidad){
        if(encarcelado == true)
            return false;
        else
            return modificarSaldo(cantidad);
    }
    
    boolean salirCarcelPagando(){
        if(encarcelado == true && puedeSalirCarcelPagando()){
            paga(precioLibertad);
            encarcelado = false;
            diario.ocurreEvento("Jugador: " + nombre + " sale carcel pagando");
            return true;
        }
        return false;
    }
    
    boolean salirCarcelTirando(){
        if(encarcelado == true){
            if(dado.salgoDeLaCarcel()){
                encarcelado = false;
                diario.ocurreEvento("Jugador: " + nombre + " sale carcel tirando");
                return true;
            }
        }
        return false;
    }
    
    boolean tieneAlgoQueGestionar(){
        return !propiedades.isEmpty();
    }
    
    boolean tieneSalvoconducto(){
        return salvoconducto != null;
    }
    
    boolean vender(int ip){
        if(encarcelado == true)
            return false;
        else{
            if(existeLaPropiedad(ip)){
                propiedades.get(ip).vender(this);
                propiedades.remove(ip);
                diario.ocurreEvento("Propiedad vendida por jugador: " + nombre);
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Jugador{" + "propiedades=" + propiedades + ", salvoconducto=" + salvoconducto + ", diario=" + diario.leerEvento() + ", encarcelado=" + encarcelado + ", nombre=" + nombre + ", numCasillaActual=" + numCasillaActual + ", puedeComprar=" + puedeComprar + ", saldo=" + saldo + '}';
    }
}
