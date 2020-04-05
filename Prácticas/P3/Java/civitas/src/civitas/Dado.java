/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;
import java.util.Random;

/**
 *
 * @author Jose Padial
 */
public class Dado {
    private static Dado INSTANCE = new Dado();
    private static final int SalidaCarcel = 5;
    
    private Random random = new Random();
    private int ultimoResultado=0;
    private boolean debug = false;
    
    private Dado(){}
    
    public static Dado getInstance(){
        return INSTANCE;
    }
    
    int tirar(){
        if(debug == false){
            ultimoResultado = 1 + random.nextInt(6);
            return ultimoResultado;
        }
        return ultimoResultado=1;
    }
    
    boolean salgoDeLaCarcel(){
        return tirar() == SalidaCarcel;
    }
    
    int quienEmpieza(int n){
        return random.nextInt(n);
    }

    public void setDebug(boolean d) {
        Diario dia= Diario.getInstance();
        if(d){
            debug=true;
            dia.ocurreEvento("Debug activado");
        }else{
            debug=false;
            dia.ocurreEvento("Debug desactivado");
        }
    }

    int getUltimoResultado() {
        return ultimoResultado;
    }      
}
