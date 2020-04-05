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
public class JugadorEspeculador extends Jugador{
    private int fianza;
    protected static final int factorEspeculador = 2;
    
    public JugadorEspeculador(Jugador otro, int fianza) {
        super(otro);
        this.fianza = fianza;
    }
    
   private boolean puedoEdificarCasa(TituloPropiedad propiedad){
        boolean puede = false;
        
        for(int i = 0; i < propiedades.size(); i++){
            if(propiedad == propiedades.get(i) && propiedades.get(i).getNumCasas() < casasMax*factorEspeculador){
                puede = true;
            }
        }
        
        return puede;
    }
    
    private boolean puedoEdificarHotel(TituloPropiedad propiedad){
        boolean puede = false;
        
        for(int i = 0; i < propiedades.size(); i++){
            if(propiedad == propiedades.get(i) && propiedades.get(i).getNumHoteles() < hotelesMax*factorEspeculador){
                if(propiedad.getNumCasas()>=getCasasPorHotel()){
                puede = true;
                }
            }
        }
        
        return puede;
    }
    
    @Override
    boolean pagaImpuesto(float cantidad){
        if(encarcelado == true){
            return false;
        }
        else{
            return paga(cantidad/2);
        }
    }
    
    @Override
    boolean encarcelar(int numCasillaCarcel){
        if(debeSerEncarcelado() == true && !paga(precioLibertad)){
            moverACasilla(numCasillaCarcel);
            encarcelado = true;
            diario.ocurreEvento("Jugador " + super.getNombre() + " encarcelado");
            return encarcelado;
        }
        else{
            return encarcelado;
        }
        
    }

    @Override
    public String toString() {
        return  "JugadorEspeculador{" + super.toString() + "fianza=" + fianza + '}';
    }
    
    
    
}
