/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;
import java.util.ArrayList;
import java.util.Collections;
/**
 *
 * @author Jose Padial
 */
public class MazoSorpresas {
    private ArrayList<Sorpresa>sorpresas;
    private boolean barajada;
    private int usadas;
    private boolean debug;
    private ArrayList<Sorpresa> cartasEspeciales;
    private Sorpresa ultimaSorpresa;
    
    
    void init(){
        sorpresas = new ArrayList<>();
        cartasEspeciales = new ArrayList<>();
        barajada = false;
        usadas = 0;
    }
    
    MazoSorpresas(){
        debug = false;
        init();
    }
    
    MazoSorpresas(boolean deb){
        debug = deb;
        init();
        if(debug == false){
            Diario diario = Diario.getInstance();
            diario.ocurreEvento("El metodo debug ya estaba activado");
        }
    }
    
    void alMazo(Sorpresa s){
        if(barajada == false){
            sorpresas.add(s);
        }
    }
    
    
    Sorpresa siguiente(){
        if(barajada == false || usadas == sorpresas.size()){
            if(debug == false){
                Collections.shuffle(sorpresas);
                usadas = 0;
                barajada = true;
            }
        }
        
        usadas = usadas + 1;
        ultimaSorpresa = sorpresas.get(0);
        sorpresas.remove(0);
        sorpresas.add(ultimaSorpresa);
        
        return ultimaSorpresa;
        
    }
    
    void inhabilitarCartaEspecial(Sorpresa sorpresa){
        boolean realizado = false;
        
        for(int i = 0; i < sorpresas.size(); i++){
            if(sorpresas.get(i) == sorpresa){
                sorpresas.remove(i);
                cartasEspeciales.add(sorpresa);
                realizado = true;
            }
        }
        
        if(realizado == true){
            Diario diario = Diario.getInstance();
            diario.ocurreEvento("Se ha inhabilitado la carta especial");
        }
         
    }
    
    void habilitarCartaEspecial(Sorpresa sorpresa){
        boolean realizado = false;
        
        for(int i = 0; i < cartasEspeciales.size(); i++){
            if(cartasEspeciales.get(i) == sorpresa){
                cartasEspeciales.remove(i);
                sorpresas.add(sorpresa);
                realizado = true;
            }
        }
        
        if(realizado == true){
           Diario diario = Diario.getInstance();
           diario.ocurreEvento("Se ha habilitado la carta especial");
        }
    }
    
    Sorpresa getUltimaSorpresa(){
        return ultimaSorpresa;
    }
    
    @Override
    public String toString(){
        return "Mazo de sorpresas {" + sorpresas + " } ";
    }
}
