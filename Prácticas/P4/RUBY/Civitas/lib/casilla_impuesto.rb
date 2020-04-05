# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

module Civitas
  class CasillaImpuesto < Casilla
    def initialize(cantidad, nombre)
      @importe = cantidad
      super(nombre)
    end
    
    def recibe_jugador(actual,todos)
      if(jugador_correcto(actual,todos))
        informe(actual, todos)
        todos[actual].paga_impuestos(@importe)
      end
    end
    
    def to_s
      to_s="CasillaImpuesto importe= #{@importe}"
    end
  end
end
