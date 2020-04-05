/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import civitas.*;

/**
 *
 * @author Jose Padial
 */
public class Controlador {
    
    CivitasJuego juegoModel;
    CivitasView vista;
    
    public Controlador(CivitasJuego juego, CivitasView look){
        juegoModel = juego;
        vista = look;
    }
    
    public void juega(){
        vista.setCivitasJuego(juegoModel);
        
        while(juegoModel.finalDelJuego() == false){
            vista.actualizarVista();
            OperacionesJuego siguiente = juegoModel.siguientePaso();
            
            if(siguiente != OperacionesJuego.PASAR_TURNO){
                vista.mostrarEventos();
            }
            
            if(juegoModel.finalDelJuego() == false){
                if(siguiente == OperacionesJuego.COMPRAR){
                    Respuestas resp = vista.comprar();
                    if(resp == Respuestas.SI){
                        juegoModel.comprar();
                        juegoModel.siguientePasoCompletado(OperacionesJuego.COMPRAR);
                    }else{
                        juegoModel.siguientePasoCompletado(OperacionesJuego.PASAR_TURNO);
                        juegoModel.pasarTurno();
                    }
                }
                else if(siguiente == OperacionesJuego.GESTIONAR){
                    vista.gestionar();
                    int gestion = vista.getGestion();
                    int propiedad = vista.getPropiedad();
                    OperacionInmobiliaria operacion = new OperacionInmobiliaria(propiedad,OperacionesInmobiliarias.values()[gestion]);
                    
                    if(operacion.getOperacion() == OperacionesInmobiliarias.VENDER){
                        juegoModel.vender(operacion.getIndicePropiedad());
                    }
                    
                    if(operacion.getOperacion() == OperacionesInmobiliarias.HIPOTECAR){
                        juegoModel.hipotecar(operacion.getIndicePropiedad());
                    }
                    
                    if(operacion.getOperacion() == OperacionesInmobiliarias.CONSTRUIR_HOTEL){
                        juegoModel.construirHotel(operacion.getIndicePropiedad());
                    }
                    
                    if(operacion.getOperacion() == OperacionesInmobiliarias.CONSTRUIR_CASA){
                        juegoModel.construirCasa(operacion.getIndicePropiedad());
                    }
                    
                    if(operacion.getOperacion() == OperacionesInmobiliarias.CANCELAR_HIPOTECA){
                        juegoModel.cancelarHipoteca(operacion.getIndicePropiedad());
                    }
                    
                    if(operacion.getOperacion() == OperacionesInmobiliarias.CONSTRUIR_HOTEL){
                        juegoModel.construirHotel(operacion.getIndicePropiedad());
                    }
                    
                    if(operacion.getOperacion() == OperacionesInmobiliarias.TERMINAR){
                        juegoModel.siguientePasoCompletado(OperacionesJuego.PASAR_TURNO);
                        juegoModel.pasarTurno();
                    }
                    
                }
                else if(siguiente == OperacionesJuego.SALIR_CARCEL){
                   SalidasCarcel salida = vista.salirCarcel();
                   
                   if(salida == SalidasCarcel.PAGANDO){
                       juegoModel.salirCarcelPagando();
                   }
                   else if(salida == SalidasCarcel.TIRANDO){
                       juegoModel.salirCarcelTirando();
                   }
                   
                   juegoModel.siguientePasoCompletado(OperacionesJuego.SALIR_CARCEL);
                }
            }
        }
        juegoModel.ranking();
    }
    
}
