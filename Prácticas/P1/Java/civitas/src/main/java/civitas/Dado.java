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
    private static final Dado INSTANCE = new Dado();
    private static final int SalidaCarcel = 5;
    
    private Random random;
    private int ultimoResultado;
    private boolean debug = false;
    
    private Dado(){}
    
    static Dado getInstance(){
        return INSTANCE;
    }
    
    int tirar(){
        if(debug == false){
            random = new Random();
            ultimoResultado = 1 + random.nextInt(6);
            return ultimoResultado;
        }
        return ultimoResultado=1;
    }
    
    boolean salgoDeLaCarcel(){
        return tirar() == SalidaCarcel;
    }
    
    int quienEmpieza(int n){
        random = new Random();
        return random.nextInt(n);
    }

    void setDebug(boolean d) {
        this.debug = d;
    }

    int getUltimoResultado() {
        return ultimoResultado;
    }      
}
