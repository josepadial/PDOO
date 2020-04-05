/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

/**
 *
 * @author Jose Padial
 */
public class TituloPropiedad {
    private float alquilerBase;
    private static final float factorInteresHipoteca = (float) 1.1;
    private float factorRevalorizacion;
    private float hipotecaBase;
    private boolean hipotecado;
    private String nombre;
    private int numCasas;
    private int numHoteles;
    private float precioCompra;
    private float precioEdificar;
    private Jugador propietario;
    
    TituloPropiedad(String nomb, float ab, float fr, float hb, float pc, float pe){
        nombre = nomb;
        alquilerBase = ab;
        factorRevalorizacion = fr;
        hipotecaBase = hb;
        precioCompra = pc;
        precioEdificar = pe;
        hipotecado = false;
        numCasas = 0;
        numHoteles = 0;
        propietario = null;
    }
    
    void actualizarPropietarioPorConversion(Jugador jugador){
        propietario = jugador;
    }
    
    boolean cancelarHipoteca(Jugador jugador){
        boolean resultado = false;
        if(hipotecado == true && esEsteElPropietario(jugador) == true){
            jugador.paga(getImporteCancelarHipoteca());
            resultado = true;
            hipotecado = false;
        }
        return resultado;
    }
    
    int cantidadCasasHoteles(){
        return (numHoteles + numCasas);
    }
    
    boolean comprar(Jugador jugador){
        boolean resultado = false;
        if(propietario == null){
            propietario = jugador;
            resultado = true;
            jugador.paga(precioCompra);
        }
        return resultado;
    }
    
    boolean construirCasa(Jugador jugador){
        boolean resultado = false;
        if(esEsteElPropietario(jugador) == true){
            jugador.paga(precioEdificar);
            numCasas= numCasas+1;
            resultado = true;
        }
        return resultado;
    }
    
    boolean construirHotel(Jugador jugador){
        boolean resultado = false;
        if(esEsteElPropietario(jugador) == true){
            jugador.paga(precioEdificar);
            numHoteles= numHoteles+1;
            resultado = true;
        }
        return resultado;
    }
    
    boolean derruirCasas(int n, Jugador jugador){
        if(jugador == propietario && numCasas >= n){
            numCasas = numCasas - n;
            return true;
        }
        else{
            return false;
        }
    }
    
    private boolean esEsteElPropietario(Jugador jugador){
        if(jugador == propietario){
            return true;
        }
        else{
            return false;
        }
    }
    
    public boolean getHipotecado(){
        return hipotecado;
    }
    
    float getImporteCancelarHipoteca(){
        return (hipotecaBase * factorInteresHipoteca);
        
    }
    
    float getImporteHipoteca(){
        return hipotecaBase;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    public int getNumCasas(){
        return numCasas;
    }
    
    public int getNumHoteles(){
        return numHoteles;
    }
    
    public  float getPrecioAlquiler(){
        
        if(hipotecado == true || propietario.getEncarcelado()){
            return 0;
        }
        else{
            return alquilerBase;
        }
    }
    
    public float getPrecioCompra(){
        return precioCompra;
    }
    
    float getPrecioEdificar(){
        return precioEdificar;
    }
    
    private float getPrecioVenta(){
        float importe = 0;
        float precioHoteles = numHoteles * precioEdificar;
        float precioCasas = numCasas * precioEdificar;
        
        importe = (precioCasas + precioHoteles) * factorRevalorizacion;
        importe = precioCompra + importe;
        
        return importe;
    }
    
    Jugador getPropietario(){
        return propietario;
    }
    
    boolean hipotecar(Jugador jugador){
        boolean salida = false;
        if(hipotecado == false && esEsteElPropietario(jugador)==true){
            jugador.recibe(getImporteHipoteca());
            hipotecado = true;
            salida = true;
        }
        return salida;
    }
    
    private boolean propietarioEncarcelado(){
        if(propietario.getEncarcelado() == true){
            return true;
        }
        else{
            return false;
        }
    }
    
    boolean tienePropietario(){
        if(propietario != null){
            return true;
        }
        else{
            return false;
        }
    }
    
    void tramitarAlquiler(Jugador jugador){
        if(propietario != null && jugador != propietario){
            jugador.paga(getPrecioAlquiler());
            propietario.recibe(getPrecioAlquiler());
        }
    }
    
    boolean vender(Jugador jugador){
        if(jugador == propietario && hipotecado == false){
            jugador.recibe(getPrecioVenta());
            propietario = null;
            numCasas = 0;
            numHoteles = 0;
            return true;
        }
        else{
            return false;
        }
    }
    
    @Override
    public String toString() {
        return "TituloPropiedad{" + "nombre=" + nombre + ", hipotecada=" + hipotecado + ", numHoteles=" + numHoteles + ", numCasas=" + numCasas + '}';
    }
 
}

