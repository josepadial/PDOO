/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

/**
 *
 * @author Jose Padial
 */
public class MazoSorpresas {
    private ArrayList<Sorpresa> sorpresas;
    private boolean barajada;
    private int usadas;
    private boolean debug;
    private ArrayList<Sorpresa> cartasEspeciales;
    private Sorpresa ultimaSorpresa;
    private Diario diario = Diario.getInstance();
    
    private void init(){
        sorpresas = new ArrayList();
        cartasEspeciales = new ArrayList();
        barajada = false;
        usadas = 0;
    }

    MazoSorpresas(){
        init();
        debug = false;
    }
    
    MazoSorpresas(boolean debug){
        this.debug = debug;
        init();
        if(this.debug == true)
            diario.ocurreEvento("MazoSorpresas en modo debug");
    }
    
    void alMazo(Sorpresa s){
        if(barajada == false)
            sorpresas.add(s);
    }
    
    Sorpresa siguiente(){
        if(barajada == false || usadas == sorpresas.size() || debug == false){
            Collections.shuffle(sorpresas);
            usadas = 0;
            barajada = true;
        }
        usadas++;
        ultimaSorpresa = sorpresas.get(0);
        sorpresas.remove(0);
        sorpresas.add(ultimaSorpresa);
        return ultimaSorpresa;
    }
    
    void inhabilitarCartaEspecial(Sorpresa sorpresa){
        if(sorpresas.indexOf(sorpresa) != -1){
            cartasEspeciales.add(sorpresas.get(sorpresas.indexOf(sorpresa)));
            sorpresas.remove(sorpresa);
            diario.ocurreEvento("Carta especial inhabilitada");
        }
    }
    
    void habilitarCartaEspecial(Sorpresa sorpresa){
        if(cartasEspeciales.indexOf(sorpresa) != -1){
            sorpresas.add(cartasEspeciales.get(cartasEspeciales.indexOf(sorpresa)));
            cartasEspeciales.remove(sorpresa);
            diario.ocurreEvento("Carta especial habilitada");  
        }
    }

    Sorpresa getUltimaSorpresa() {
        return ultimaSorpresa;
    }
        
}
