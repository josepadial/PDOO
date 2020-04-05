# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

require_relative"mazo_sorpresas.rb"
require_relative"titulo_propiedad.rb"
require_relative"sorpresa.rb"
require_relative"diario.rb"

module Civitas
  class Casilla
    
    def initialize(nombre)
      @nombre = nombre
    end

    private
    
    def informe(actual, todos)
      @diario = Diario.instance
      @diario.ocurre_evento("El jugador " + todos.at(actual).get_nombre + " ha caido en la casilla " + to_s)
    end
   
     public 
    def get_nombre
      @nombre
    end
    
    def jugador_correcto(actual,todos)
      if(todos.at(actual) != nil)
            return true
        else
            return false
      end
    end

    def recibe_jugador(actual,todos)
      informe(actual,todos)
    end
    
    def to_s
      to_s="Nombre #{@nombre}"
    end

  end
end
