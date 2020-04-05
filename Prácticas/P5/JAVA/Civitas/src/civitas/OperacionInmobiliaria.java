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
public class OperacionInmobiliaria {
    private int indicePropiedad;
    private OperacionesInmobiliarias operacion;
    
    public OperacionInmobiliaria(int indice, OperacionesInmobiliarias operac){
        indicePropiedad = indice;
        operacion = operac;
    }
    
    public int getIndicePropiedad(){
        return indicePropiedad;
    }
    
    public OperacionesInmobiliarias getOperacion(){
        return operacion;
    }
    
}
