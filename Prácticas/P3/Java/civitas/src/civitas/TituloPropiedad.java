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
    private Jugador propietario;
    private float alquilerBase;
    private static float factorInteresHipoteca = (float) 1.1;
    private float factorRevalorizacion;
    private float hipotecaBase;
    private boolean hipotecado;
    private String nombre;
    private int numCasas;
    private int numHoteles;
    private float precioCompra;
    private float precioEdificar;
    
    TituloPropiedad(String nom, float ab, float fr, float hb, float pc, float pe){
        this.nombre = nom;
        this.alquilerBase = ab;
        this.factorRevalorizacion = fr;
        this.hipotecaBase = hb;
        this.precioCompra = pc;
        this.precioEdificar = pe;
        this.hipotecado=false;
        this.numCasas=0;
        this.numHoteles=0;
        this.propietario=null;
    }
    
    void actualizarPropietarioPorConversion(Jugador jugador){
        if(!esEsteElPropietario(jugador))
            this.propietario = jugador;
    }
    
    boolean cancelarHipoteca(Jugador jugador){
        if(hipotecado && esEsteElPropietario(jugador)){
            if(propietario.paga(getImporteCancelarHipoteca())){
                hipotecado = false;
                return true;
            }
        }
        return false;
    }
    
    int cantidadCasasHoteles(){
       return numCasas+numHoteles; 
    }
    
    boolean comprar(Jugador jugador){
        if(!tienePropietario()){
            propietario = jugador;
            propietario.paga(precioCompra);
            return true;
        }
        return false;
    }
    
    boolean construirCasa(Jugador jugador){
        if(esEsteElPropietario(jugador) && numCasas < 4 && jugador.getSaldo() > precioEdificar){
            propietario.paga(precioEdificar);
            numCasas++;
            return true;
        }
        return false;
    }
    
    boolean construirHotel(Jugador jugador){
        if(esEsteElPropietario(jugador) && numHoteles < 4 && numCasas == 4 && jugador.getSaldo() > precioEdificar){
            propietario.paga(precioEdificar);
            numCasas -= 4;
            numHoteles++;
            return true;
        }
        return false;
    }
    
    boolean derruirCasas(int n, Jugador jugador){
        if(esEsteElPropietario(jugador) && numCasas >= n){
            numCasas -= n;
            return true;
        }
        return false;
    }
    
    private boolean esEsteElPropietario(Jugador jugador){
        return (propietario == jugador && tienePropietario());
    }

    Jugador getPropietario() {
        return propietario;
    }

    public boolean getHipotecado() {
        return hipotecado;
    }

    public String getNombre() {
        return nombre;
    }

    int getNumCasas() {
        return numCasas;
    }

    int getNumHoteles() {
        return numHoteles;
    }
    
    private float getPrecioAlquiler(){
        if(hipotecado == true || propietarioEcarcelado() == true)
            return 0;
        else
            return (float) (alquilerBase*(1+(numCasas*0.5)+(numHoteles*2.5)));
    }

    float getPrecioCompra() {
        return precioCompra;
    }

    float getPrecioEdificar() {
        return precioEdificar;
    }
    
    float getImporteCancelarHipoteca(){
        return getImporteHipoteca()*factorInteresHipoteca;
    }
    
    private float getImporteHipoteca(){
        return (float) (hipotecaBase*(1+(numCasas*0.5)+(numHoteles*2.5)));
    }
    
    private float getPrecioVenta(){
        return (float) precioCompra+(numCasas+5*numHoteles)*precioEdificar*factorRevalorizacion;
    }
    
    boolean hipotecar(Jugador jugador){
        if(!hipotecado && esEsteElPropietario(jugador)){
            if(propietario.recibe(getImporteHipoteca())){
                hipotecado = true;
                return true;
            }  
        }
        return false;
    }
    
    private boolean propietarioEcarcelado(){
        return propietario.isEncarcelado();
    }
    
    boolean tienePropietario(){
        return propietario != null;
    }
    
    void tramitarAlquiler(Jugador jugador){
        if(tienePropietario() && !esEsteElPropietario(jugador)){
            jugador.pagaAlquiler(getPrecioAlquiler());
            propietario.recibe(getPrecioAlquiler());
        }
    }
    
    boolean vender(Jugador jugador){
        if(esEsteElPropietario(jugador) && !hipotecado){
            jugador.recibe(getPrecioVenta());
            propietario = null;
            numCasas = 0;
            numHoteles = 0;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "TituloPropiedad{" +"Nombre= "+ nombre + ", alquilerBase=" + alquilerBase + ", factorRevalorizacion=" + factorRevalorizacion + ", hipotecaBase=" + hipotecaBase + ", hipotecado=" + hipotecado + ", numCasas=" + numCasas + ", numHoteles=" + numHoteles + ", precioCompra=" + precioCompra + ", precioEdificar=" + precioEdificar + '}';
    }
}
