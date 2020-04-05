# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

require_relative "tablero.rb"
require_relative "diario.rb"
require_relative "jugador.rb"
require_relative "mazo_sorpresas.rb"

module Civitas
class Sorpresa
  
    def initialize
      
    end

  public
    def aplicar_a_jugador(actual,todos)
      
    end
    
    def jugador_correcto(actual,todos)
      if(todos.at(actual) != nil)
        return true
      else
        return false
      end
    end
    
    def informe(actual,todos)
      @diario = Diario.instance
      @diario.ocurre_evento("Se aplica una sorpresa al jugador" + todos.at(actual).get_nombre + " de tipo " + @tipo.to_s)
    end
    
end
end