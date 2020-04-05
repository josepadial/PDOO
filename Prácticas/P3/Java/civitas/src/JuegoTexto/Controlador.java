/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JuegoTexto;


import civitas.CivitasJuego;
import civitas.OperacionesInmobiliarias;
import civitas.OperacionesJuego;
import civitas.Respuestas;
import civitas.SalidasCarcel;
import civitas.OperacionInmobiliaria;

/**
 *
 * @author Jose Padial
 */
class Controlador {
    private CivitasJuego juego;
    private VistaTextual vista;
    
    Controlador(CivitasJuego juego, VistaTextual vista){
        this.juego=juego;
        this.vista=vista;
    }
    
    void juega(){
        vista.setCivitasJuego(juego);
        while(!juego.finalDelJuego()){
            System.out.println("");
            vista.actualizarVista();
            vista.pausa();
            OperacionesJuego operacion = juego.siguientePaso();
            vista.mostrarSiguienteOperacion(operacion);
            if(operacion!=OperacionesJuego.PASAR_TURNO){
                vista.mostrarEventos();
            }
            if(!juego.finalDelJuego()){
                switch(operacion){
                    case COMPRAR:
                        Respuestas respuesta=vista.comprar();
                        if(respuesta==Respuestas.SI){
                            juego.comprar();
                        }
                        juego.siguientePasoCompletado(operacion);
                        break;
                    
                    case GESTIONAR:
                        vista.gestionar();
                        int igest=vista.getGestion();
                        int ip=vista.getPropiedad();
                        OperacionInmobiliaria op=new OperacionInmobiliaria(OperacionesInmobiliarias.values()[igest], ip);
                        switch(OperacionesInmobiliarias.values()[igest]){
                            case VENDER:
                                juego.vender(ip);
                                break;
                            case HIPOTECAR:
                                juego.hipotecar(ip);
                                break;
                            case CANCELAR_HIPOTECA:
                                juego.cancelarHipoteca(ip);
                                break;
                            case CONSTRUIR_CASA:
                                juego.construirCasa(ip);
                                break;
                            case CONSTRUIR_HOTEL:
                                juego.construirHotel(ip);
                                break;
                            case TERMINAR:
                                juego.siguientePasoCompletado(operacion);
                                break;
                        }
                        break;
                    case SALIR_CARCEL:
                        SalidasCarcel salida= vista.salirCarcel();
                        if(salida==SalidasCarcel.PAGANDO)
                            juego.salirCarcelPagando();
                        else
                            juego.salirCarcelTirando();
                        juego.siguientePasoCompletado(operacion);
                        break;
                    }
            }
        }
        juego.ranking();
    }
}