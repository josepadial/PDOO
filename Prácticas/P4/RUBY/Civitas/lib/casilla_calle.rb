# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

require_relative "casilla.rb"

module Civitas
  class CasillaCalle < Casilla
    attr_reader :titulo
    def initialize(titulo)
      @titulo = titulo
      super(@titulo.get_nombre)
    end
    
    def recibe_jugador(actual,todos)
      if(jugador_correcto(actual,todos) == true)
        informe(actual,todos);
        
        if(@titulo.tiene_propietario == false)
          todos.at(actual).puede_comprar_casilla
        else
          @titulo.tramitar_alquiler(todos.at(actual))
        end
      end
    end
    
    def to_s
      to_s="Titulo #{@titulo}"
    end
  end
end
