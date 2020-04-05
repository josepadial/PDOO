# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

require_relative "tablero.rb"
require_relative "diario.rb"
require_relative "jugador.rb"
require_relative "mazo_sorpresas.rb"

module Civitas
  class SorpresaIrCasilla < Sorpresa
    def initialize(tablero,valor,texto)
      @tablero = tablero
      @valor = valor
      @texto = texto
    end
    
    def aplicar_a_jugador(actual,todos)
      if(jugador_correcto(actual,todos))
        informe(actual,todos)
        casilla_actual = todos[actual].get_num_casilla_actual
        tirada = @tablero.calcular_tirada(casilla_actual, @valor)
        new_pos = @tablero.nueva_posicion(casilla_actual, tirada)
        todos[actual].mover_a_casilla(new_pos)
        @tablero.get_casilla(new_pos).recibe_jugador(actual,todos)
      end
    end
    
    def to_s
      to_s="Sorpresa ir a casilla {#{@texto}, valor=#{@valor}}"
    end
  end
end
