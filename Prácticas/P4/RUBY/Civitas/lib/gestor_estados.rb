# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

require_relative"diario.rb"
require_relative "operaciones_juego.rb"
require_relative"estados_juego.rb"

module Civitas
  class Gestor_estados

    def estado_inicial
      return (Estados_juego::INICIO_TURNO)
    end

    def operaciones_permitidas(jugador,estado)
      op = nil

      case estado

      when Estados_juego::INICIO_TURNO
        if (jugador.get_encarcelado)
          op = Operaciones_juego::SALIR_CARCEL
        else
          op = Operaciones_juego::AVANZAR
        end

      when Estados_juego::DESPUES_CARCEL
        op = Operaciones_juego::PASAR_TURNO

      when Estados_juego::DESPUES_AVANZAR
        if (jugador.get_encarcelado)
          op = Operaciones_juego::PASAR_TURNO
        else
          if (jugador.get_puede_comprar)
            op = Operaciones_juego::COMPRAR
          else
            if (jugador.tiene_algo_que_gestionar)
              op = Operaciones_juego::GESTIONAR
            else
              op = Operaciones_juego::PASAR_TURNO
            end
          end
        end

      when Estados_juego::DESPUES_COMPRAR
        if (jugador.tiene_algo_que_gestionar)
          op = Operaciones_juego::GESTIONAR
        else
          op = Operaciones_juego::PASAR_TURNO
        end

      when Estados_juego::DESPUES_GESTIONAR
        op = Operaciones_juego::PASAR_TURNO
      end

      return op
    end



    def siguiente_estado(jugador,estado,operacion)
      siguiente = nil
      puts "OPERACION " + operacion.to_s
      puts "ESTADO " + estado.to_s
      
      case estado

      when Estados_juego::INICIO_TURNO
        if (operacion==Operaciones_juego::SALIR_CARCEL)
          siguiente = Estados_juego::DESPUES_CARCEL
        else
          if (operacion==Operaciones_juego::AVANZAR)
            siguiente = Estados_juego::DESPUES_AVANZAR
          end
        end


      when Estados_juego::DESPUES_CARCEL
        if (operacion==Operaciones_juego::PASAR_TURNO)
          siguiente = Estados_juego::INICIO_TURNO
        end

      when Estados_juego::DESPUES_AVANZAR
        case operacion
          when Operaciones_juego::PASAR_TURNO
            siguiente = Estados_juego::INICIO_TURNO
          when
            Operaciones_juego::COMPRAR
              siguiente = Estados_juego::DESPUES_COMPRAR
          when Operaciones_juego::GESTIONAR
              siguiente = Estados_juego::DESPUES_GESTIONAR
        end


      when Estados_juego::DESPUES_COMPRAR
        if (jugador.tiene_algo_que_gestionar)
          if (operacion==Operaciones_juego::GESTIONAR)
            siguiente = Estados_juego::DESPUES_GESTIONAR
          end
        end
        
        if (operacion==Operaciones_juego::PASAR_TURNO)
            siguiente = Estados_juego::INICIO_TURNO
        end

      when Estados_juego::DESPUES_GESTIONAR
        if (operacion==Operaciones_juego::PASAR_TURNO)
          siguiente = Estados_juego::INICIO_TURNO
        end
      end

      Diario.instance.ocurre_evento("De: "+estado.to_s+ " con "+operacion.to_s+ " sale: "+siguiente.to_s)

      return siguiente
    end

  end
end

