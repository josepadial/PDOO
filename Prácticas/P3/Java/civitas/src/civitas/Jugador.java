/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

import static java.lang.Float.compare;
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
        boolean salida = false;
        if(encarcelado)
            return salida;
        else{
            if(existeLaPropiedad(ip)){
                TituloPropiedad propiedad = propiedades.get(ip);
                float cantidad = propiedad.getImporteCancelarHipoteca();
                if(puedoGastar(cantidad)){
                    salida = propiedad.cancelarHipoteca(this);
                    if(salida)
                        diario.ocurreEvento("El jugador " + nombre + " calcela la hipooteca de " + ip);
                }
            }
        }
        return salida;
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
        return compare(this.saldo, otro.saldo);
    }
    
    boolean comprar(TituloPropiedad titulo){
       boolean salida = false;
       
       if(encarcelado)
           return salida;
       else{
           if(puedeComprar){
               float precio = titulo.getPrecioCompra();
               if(puedoGastar(precio)){
                   salida = titulo.comprar(this);
                   if(salida){
                       propiedades.add(titulo);
                       diario.ocurreEvento("El jugador" + nombre + "  compra la propiedad "+titulo.toString());
                   }
                   puedeComprar = false;
               }
           }
       }
       return salida;
    }
    
    boolean construirCasa(int ip){
        boolean salida = false;
        
        if(encarcelado)
            return salida;
        else{
            if(existeLaPropiedad(ip)){
                TituloPropiedad propiedad = propiedades.get(ip);
                boolean puedoEdificarCasa = puedoEdificarCasa(propiedad);
                float precio = propiedad.getPrecioEdificar();
                if(puedoGastar(precio) && (propiedad.getNumCasas()<getCasasMax())){
                    puedoEdificarCasa = true;
                }
                if(puedoEdificarCasa){
                    salida = propiedad.construirCasa(this);
                }
                diario.ocurreEvento("El jugador "+nombre+ "  construye casa en la propiedad "+ip);
            }
        }
        return salida;
    }
    
    boolean construirHotel(int ip){
        boolean salida = false;
        if(encarcelado)
            return salida;
        else{
            if(existeLaPropiedad(ip)){
                TituloPropiedad propiedad = propiedades.get(ip);
                boolean puedoEdificarHotel = puedoEdificarHotel(propiedad);
                float precio = propiedad.getPrecioEdificar();
                if(puedoGastar(precio) && (propiedad.getNumHoteles()<getHotelesMax()) && (propiedad.getNumCasas()>=getCasasPorHotel()))
                    puedoEdificarHotel = true;
                if(puedoEdificarHotel){
                    salida = propiedad.construirHotel(this);
                    int casasPorHotel = getCasasPorHotel();
                    propiedad.derruirCasas(casasPorHotel, this);
                }
                diario.ocurreEvento("El jugador "+nombre+ "  construye hotel en la propiedad "+ip);
            }
        }
        return salida;
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

    public ArrayList<TituloPropiedad> getPropiedades() {
        return propiedades;
    }
    
    public ArrayList<String> cadenaPropiedades(){
        ArrayList<String> cadena = new ArrayList();
        
        for(TituloPropiedad t:propiedades){
            cadena.add(t.getNombre());
        }
        
        return cadena;
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
        boolean salida = false;
        if(encarcelado)
            return salida;
        else{
            if(existeLaPropiedad(ip)){
                TituloPropiedad propiedad = propiedades.get(ip);
                salida = propiedad.hipotecar(this);
            }
            if(salida)
                diario.ocurreEvento("El jugador "+nombre+ "  hipoteca la propiedad "+ip);
        }
        return salida;
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
            diario.ocurreEvento("Jugador " + nombre + " movido a la casilla: " + numCasilla);
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
        return "Jugador{" + "Nombre= "+ nombre+ ", encarcelado=" + encarcelado + ", numCasillaActual=" + numCasillaActual + ", puedeComprar=" + puedeComprar + ", saldo=" + saldo + ", propiedades=" + propiedades + ", salvoconducto=" + salvoconducto + '}';
    }
}
