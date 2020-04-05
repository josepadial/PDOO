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
    private int numPropiedad;
    private OperacionesInmobiliarias gestion;

    public OperacionInmobiliaria(OperacionesInmobiliarias gest, int ip) {
        this.numPropiedad = ip;
        this.gestion = gest;
    }

    public int getNumPropiedad() {
        return numPropiedad;
    }

    public OperacionesInmobiliarias getGestion() {
        return gestion;
    }
}
