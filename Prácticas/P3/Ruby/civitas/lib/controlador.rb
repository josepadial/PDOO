# encoding: utf-8
# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

require_relative "civitas_juego.rb"
require_relative "operacion_inmobiliaria.rb"
require_relative "operaciones_juego.rb"
require_relative "respuestas.rb"
require_relative "operaciones_inmobiliarias.rb"
require_relative "salidas_carcel.rb"

module JuegoTexto
  class Controlador
    include Civitas
    def initialize(juego,vista)
      @juego = juego
      @vista = vista
    end
    
    def juega
      @vista.setCivitasJuego(@juego)
      while !@juego.final_del_juego
        @vista.actualizarVista
        @vista.pausa
        @operacion = @juego.siguiente_paso
        @vista.mostrarSiguienteOperacion(@operacion)
        if @operacion != OperacionesJuego::PASARTURNO
          @vista.mostrarEventos
        end
        if !@juego.final_del_juego
          case @operacion
          when OperacionesJuego::COMPRAR
            @respuesta = @vista.comprar
            if @respuesta == Respuesta::SI
              @juego.comprar
            end
            @juego.siguiente_paso_completado(@operacion)
          when OperacionesJuego::GESTIONAR
            @vista.gestionar
            @igest = @vista.i_gestion
            @ip = @vista.i_propiedad
            lista_respuestas = [OperacionesInmobiliarias::VENDER,OperacionesInmobiliarias::HIPOTECAR,OperacionesInmobiliarias::CANCELAR_HIPOTECA,OperacionesInmobiliarias::CONSTRUIR_CASA,OperacionesInmobiliarias::CONSTRUIR_HOTEL,OperacionesInmobiliarias::TERMINAR]
            @op = OperacionInmobiliaria.new(lista_respuestas[@igest],@ip)
            case lista_respuestas[@igest]
            when OperacionInmobiliaria::VENDER
              @juego.vender(@ip)
            when OperacionInmobiliaria::HIPOTECAR
              @juego.hipotecar(@ip)
            when OperacionInmobiliaria::CANCELARHIPOTECA
              @juego.cancelar_hipoteca(@ip)
            when OperacionInmobiliaria::CONSTRUIRCASA
              @juego.construir_casa(@ip)
            when OperacionInmobiliaria::CONSTRUIRHOTEL
              @juego.construir_hotel(@ip)
            when OperacionInmobiliaria::TERMINAR
              @juego.siguiente_paso_completado(@operacion)
            end
          when OperacionesJuego::SALIRCARCEL
            @salida = @vista.salir_carcel
            if @salida == SalidasCarcel::PAGANDO
              @juego.salir_carcel_pagando
            else
              @juego.salir_carcel_tirando
            end
            @juego.siguiente_paso_completado(@operacion)
          end
        end
      end
      
      @juego.ranking
    end
  end
end
