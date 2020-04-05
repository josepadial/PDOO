# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

require_relative "tablero.rb"
require_relative "diario.rb"
require_relative "jugador.rb"
require_relative "mazo_sorpresas.rb"

module Civitas
  class SorpresaSalidaCarcel < Sorpresa
    def initialize(mazo)
      @mazo = mazo
    end
    
    def aplicar_a_jugador_(actual,todos)
      
      la_tiene = false
      
      if(todos[actual] != nil)
        informe(actual,todos)
        for i in todos
          if(i.tiene_salvo_conducto == true)
            la_tiene = true
          end
        end
        
        if(la_tiene == false)
          todos[actual].obtener_salvo_conducto(self)
          salir_del_mazo
        end
        
      end
      
    end
    
    def salir_del_mazo()
      @mazo.inhabilitar_carta_especial(self)
    end
    
    def usadas()
      @mazo.habilitar_carta_especial(self)
    end
    
    def to_s
      to_s="Sorpresa salir de la carcel {#{@mazo}}"
    end
  end
end
