# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

require_relative "tablero.rb"
require_relative "diario.rb"
require_relative "jugador.rb"
require_relative "mazo_sorpresas.rb"

module Civitas
  class SorpresaPorJugador < Sorpresa
    def initialize(tablero,valor,texto)
      @tablero = tablero
      @valor = valor
      @texto = texto
    end
    
    def aplicar_a_jugador(actual,todos)
      if(jugador_correcto(actual,todos))
        informe(actual,todos)
        sorpresa = Sorpresa.new((@valor*-1),nil,@tablero,"Jugadores pagan",Tipo_sorpresa::PAGARCOBRAR)
        
        for i in (0..todos.length)
          if(todos.at(i) != todos[actual])
            sorpresa.aplicar_a_jugador_pagar_cobrar(i,todos)
          end
        end
        
        num_jugadores = todos.length - 1
        
        sorpresa2 = Sorpresa.new((@valor * num_jugadores),nil,@tablero, "Jugador actual recibe",Tipo_sorpresa::PAGARCOBRAR)
        sorpresa2.aplicar_a_jugador_pagar_cobrar(actual,todos)
      end
      
    end
    
    def to_s
      to_s="Sorpresa por jugador {#{@texto}, valor=#{@valor}}"
    end
  end
end
