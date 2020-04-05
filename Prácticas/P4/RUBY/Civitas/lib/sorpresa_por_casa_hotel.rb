# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

require_relative "tablero.rb"
require_relative "diario.rb"
require_relative "jugador.rb"
require_relative "mazo_sorpresas.rb"

module Civitas
  class SorpresaPorCasaHotel < Sorpresa
    def initialize(tablero,valor,texto)
      @tablero = tablero
      @valor = valor
      @texto = texto
    end
    
    def aplicar_a_jugador(actual,todos)
      if(jugador_correcto(actual,todos))
        informe(actual,todos)
        num_casas_hoteles = todos[actual].cantidad_casas_hoteles
        importe = @valor*num_casas_hoteles
        todos[actual].modificar_saldo(importe)
      end
    end
    
    def to_s
      to_s="Sorpresa por casa hotel {#{@texto}, valor=#{@valor}}"
    end
  end
end
