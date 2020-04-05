# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

require_relative "tablero.rb"
require_relative "diario.rb"
require_relative "jugador.rb"
require_relative "mazo_sorpresas.rb"

module Civitas
  class SorpresaIrCarcel < Sorpresa
    def initialize(tablero,texto)
      @tablero = tablero
      @texto = texto
    end
    
    def aplicar_a_jugador(actual,todos)
      if(jugador_correcto(actual,todos))
        informe(actual,todos)
        todos[actual].encarcelar(@tablero.get_carcel)
      end
    end
    
    def to_s
      to_s="Sorpresa ir a carcel {#{@texto}, valor=#{@valor}}"
    end
  end
end
