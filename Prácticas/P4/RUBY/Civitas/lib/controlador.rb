# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

require_relative"vista_textual.rb"
require_relative"civitas_juego.rb"

module Civitas
class Controlador
  
  public
  def initialize(juego,look)
    @juego_model = juego
    @vista = look
  end
  
  def juega
    @vista.set_civitas_juego(@juego_model)
    
    while(@juego_model.final_del_juego == false)
      
      @vista.actualizar_vista
      @vista.pausa
      siguiente = @juego_model.siguiente_paso
            
      if(siguiente != Operaciones_juego::PASAR_TURNO)
        @vista.mostrar_eventos
      end
      
      if(@juego_model.final_del_juego == false)
         if(siguiente == Operaciones_juego::COMPRAR)
            resp = @vista.comprar
            if(resp == Respuestas::SI)
                @juego_model.comprar
                @juego_model.siguiente_paso_completado(Operaciones_juego::COMPRAR)
            else
                @juego_model.siguiente_paso_completado(Operaciones_juego::PASAR_TURNO)
                @juego_model.pasar_turno
            end
         end
         
         if(siguiente == Operaciones_juego::GESTIONAR)
            lista_operaciones_inmobiliarias = [Operaciones_inmobiliarias::VENDER,
                                               Operaciones_inmobiliarias::HIPOTECAR,
                                               Operaciones_inmobiliarias::CANCELAR_HIPOTECA,
                                               Operaciones_inmobiliarias::CONSTRUIR_CASA,
                                               Operaciones_inmobiliarias::CONSTRUIR_HOTEL,
                                               Operaciones_inmobiliarias::TERMINAR]
            @vista.gestionar
            gestion = @vista.get_gestion
            propiedad = @vista.get_propiedad
            operacion = Operacion_inmobiliaria.new(propiedad,lista_operaciones_inmobiliarias[gestion])
                    
            if(operacion.get_operacion == Operaciones_inmobiliarias::VENDER)
                @juego_model.vender(operacion.get_indice_propiedad)
            end
                    
            if(operacion.get_operacion == Operaciones_inmobiliarias::HIPOTECAR)
                @juego_model.hipotecar(operacion.get_indice_propiedad)
            end
                    
            if(operacion.get_operacion == Operaciones_inmobiliarias::CONSTRUIR_HOTEL)
                @juego_model.construir_hotel(operacion.get_indice_propiedad)
            end
                    
            if(operacion.get_operacion == Operaciones_inmobiliarias::CONSTRUIR_CASA)
                @juego_model.construir_casa(operacion.get_indice_propiedad)
            end
                    
            if(operacion.get_operacion == Operaciones_inmobiliarias::CANCELAR_HIPOTECA)
                @juego_model.cancelar_hipoteca(operacion.get_indice_propiedad)
            end
                    
            if(operacion.get_operacion == Operaciones_inmobiliarias::TERMINAR)
                @juego_model.siguiente_paso_completado(Operaciones_juego::PASAR_TURNO)
                @juego_model.pasar_turno
            end
                    
         end
         
          if(siguiente == Operaciones_juego::SALIR_CARCEL)
              salida = @vista.salir_carcel
                   
              if(salida == Salidas_carcel::PAGANDO)
                @juego_model.salir_carcel_pagando
              end
                   
              if(salida == Salidas_carcel::TIRANDO)
                @juego_model.salir_carcel_tirando
              end
                   
              @juego_model.siguiente_paso_completado(Operaciones_juego::SALIR_CARCEL)
          end
        
      end
      
    end
    @juego_model.ranking
  end
  
end
end