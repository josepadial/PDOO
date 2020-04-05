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

public class Diario {
  static final private Diario instance = new Diario();
  
  private ArrayList<String> eventos;
  
  static public Diario getInstance() {
    return instance;
  }
  
  private Diario () {
    eventos = new ArrayList<>();
  }
  
  void ocurreEvento (String e) {
    eventos.add (e);
  }
  
  public boolean eventosPendientes () {
    if(eventos.isEmpty() == true){
        return false;
    }
    else{
        return true;
    }
  }
  
  public String leerEvento () {
    String salida = "";
    if (!eventos.isEmpty()) {
      salida = eventos.get(0);
      eventos.remove(0);
    }
    return salida;
  }
}
