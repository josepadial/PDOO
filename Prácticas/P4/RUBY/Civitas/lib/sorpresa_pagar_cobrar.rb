# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

require_relative "tablero.rb"
require_relative "diario.rb"
require_relative "jugador.rb"
require_relative "mazo_sorpresas.rb"

module Civitas
  class SorpresaPagarCobrar < Sorpresa
    def initialize(tablero,valor,texto)
      @tablero = tablero
      @valor = valor
      @texto = texto
    end
    
    def aplicar_a_jugador(actual,todos)
      if(jugador_correcto(actual,todos))
        informe(actual,todos)
        todos[actual].modificar_saldo(@valor)
      end
    end
    
    def to_s
      to_s="Sorpresa pagar cobrar {#{@texto}, valor=#{@valor}}"
    end
  end
end
