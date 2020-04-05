package JuegoTexto;

import civitas.CivitasJuego;
import civitas.Diario;
import civitas.OperacionesJuego;
import civitas.SalidasCarcel;
import civitas.Respuestas;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import civitas.Casilla;
import civitas.Jugador;
import civitas.OperacionesInmobiliarias;
import civitas.TituloPropiedad;

class VistaTextual {
  
  CivitasJuego juegoModel;
  private int iGestion=-1;
  private int iPropiedad=-1;
  private static String separador = "=====================";
  
  private Scanner in;
  
  VistaTextual () {
    in = new Scanner (System.in);
  }
  
  void mostrarEstado(String estado) {
    System.out.println (estado);
  }
              
  void pausa() {
    System.out.print ("Pulsa una tecla");
    in.nextLine();
  }

  int leeEntero (int max, String msg1, String msg2) {
    Boolean ok;
    String cadena;
    int numero = -1;
    do {
      System.out.print (msg1);
      cadena = in.nextLine();
      try {  
        numero = Integer.parseInt(cadena);
        ok = true;
      } catch (NumberFormatException e) { // No se ha introducido un entero
        System.out.println (msg2);
        ok = false;  
      }
      if (ok && (numero < 0 || numero >= max)) {
        System.out.println (msg2);
        ok = false;
      }
    } while (!ok);

    return numero;
  }

  int menu (String titulo, ArrayList<String> lista) {
    String tab = "  ";
    int opcion;
    System.out.println (titulo);
    for (int i = 0; i < lista.size(); i++) {
      System.out.println (tab+i+"-"+lista.get(i));
    }

    opcion = leeEntero(lista.size(),
                          "\n"+tab+"Elige una opción: ",
                          tab+"Valor erróneo");
    return opcion;
  }

  SalidasCarcel salirCarcel() {
    int opcion = menu ("Elige la forma para intentar salir de la carcel",
      new ArrayList<> (Arrays.asList("Pagando","Tirando el dado")));
    return (SalidasCarcel.values()[opcion]);
  }

  Respuestas comprar() {
    int opcion = menu (juegoModel.getCasillaActual().toString()+ "\n¿Desea comprar la calle?",
        new ArrayList<> (Arrays.asList("Si", "No")));
    return (Respuestas.values()[opcion]);
  }

  void gestionar() {
    iGestion = menu ("Eliga la gestion que desea hacer",
        new ArrayList<> (Arrays.asList("Vender", "Hipotecar", "Cancelar Hipoteca", "Construir Casa", "Construir Hotel", "Terminar")));
    
    ArrayList<String> calles = new ArrayList<String>();
    
    for(int i=0; i<juegoModel.getJugadorActual().getPropiedades().size(); i++){
        calles.add(juegoModel.getJugadorActual().getPropiedades().get(i).getNombre());
    }
    iPropiedad = menu("¿Sobre que casilla desea operar?",calles);
  }
  
  public int getGestion(){
      return iGestion;
  }
  
  public int getPropiedad(){
      return iPropiedad;
  }   

  void mostrarSiguienteOperacion(OperacionesJuego operacion) {
      switch(operacion){
          case AVANZAR:
              System.out.println("La siguiente operacion es avanzar");
              break;
          case COMPRAR:
              System.out.println("La siguiente operacion es comprar");
              break;
          case GESTIONAR:
              System.out.println("La siguiente operacion es gestionar");
              break;
          case PASAR_TURNO:
              System.out.println("La siguiente operacion es pasar turno");
              break;
          default:
              System.out.println("La siguiente operacion es salir de la carcel");        
      }
  }

  void mostrarEventos() {
      while(Diario.getInstance().eventosPendientes()){
          System.out.println(Diario.getInstance().leerEvento());
      }
  }
  
  public void setCivitasJuego(CivitasJuego civitas){ 
        juegoModel=civitas;
    }
  
  public void actualizarVista(){
      System.out.print(juegoModel.infoJugadorTexto());
  } 
}