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
public class TestP1 {

    /**
     * @param args the command line arguments
     */
    
    private static final int numJugadores = 4;
    private static Dado dado = Dado.getInstance();
    
    public static void main(String[] args) {
        int cero = 0, uno = 0, dos = 0, tres = 0, otro = 0;
        MazoSorpresas mazo = new MazoSorpresas();
        
        for(int i = 0; i < 100; ++i){
            int valor = dado.quienEmpieza(numJugadores);
            switch(valor){
                case 0: cero++; break;
                case 1: uno++; break;
                case 2: dos++; break;
                case 3: tres++; break;
                default: otro++; break;
            }
        }
        
        System.out.println("\t\tValores de quien empieza");
        System.out.println("Cero: " + cero);
        System.out.println("Uno: " + uno);
        System.out.println("Dos: " + dos);
        System.out.println("Tres: " + tres);
        System.out.println("Otro: " + otro);
        
        System.out.println("\t\tValor de dado debug = off");
        dado.setDebug(false);
        System.out.println("Tirada uno: " + dado.tirar());
        System.out.println("Tirada dos: " + dado.tirar());
        
        System.out.println("\t\tValor de dado debug = on");
        dado.setDebug(true);
        System.out.println("Tirada uno: " + dado.tirar());
        System.out.println("Tirada dos: " + dado.tirar());
        
        System.out.println("\t\tUltimo resultado de DADO");
        dado.setDebug(false);
        System.out.println("Tirada uno: " + dado.tirar());
        System.out.println("Resultado almacenado: " + dado.getUltimoResultado());
        System.out.println("Tirada dos: " + dado.tirar());
        System.out.println("Resultado almacenado: " + dado.getUltimoResultado());
        
        System.out.println("\t\tSalgo de la carcel DADO");
        System.out.println("Tirada uno: " + dado.salgoDeLaCarcel() + "  " + dado.getUltimoResultado());
        System.out.println("Tirada dos: " + dado.salgoDeLaCarcel() + "  " + dado.getUltimoResultado());
        System.out.println("Tirada tres: " + dado.salgoDeLaCarcel() + "  " + dado.getUltimoResultado());
        
        Sorpresa a = new Sorpresa();
        Sorpresa b = new Sorpresa();
        mazo.alMazo(a);
        mazo.alMazo(b);
        
        mazo.siguiente();
        
        mazo.inhabilitarCartaEspecial(b);
        mazo.habilitarCartaEspecial(b);
    }
    
}
