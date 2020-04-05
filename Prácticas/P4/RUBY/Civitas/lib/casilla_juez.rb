# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

module Civitas
  class CasillaJuez < Casilla
    def initialize(nombre,num_casilla_carcel)
      @@carcel = num_casilla_carcel
      super(nombre)
    end
    
    def recibe_jugador(actual,todos)
      if(jugador_correcto(actual,todos))
        informe(actual, todos)
        todos[actual].encarcelar(@@carcel)
      end
    end
    
    def to_s
      to_s="CasillaJuez carcel= #{@@carcel}"
    end
  end
end
