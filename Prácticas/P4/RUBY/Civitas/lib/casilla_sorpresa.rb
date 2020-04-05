# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

module Civitas
  class Casilla_Sorpresa < Casilla
    def initialize(nombre,mazo)
      super(nombre)
      @mazo = mazo
      @sorpresa = nil
    end
    
    def recibe_jugador_sorpresa(actual,todos)
      if(jugador_correcto(actual,todos) == true)
        @sorpresa = @mazo.siguiente
        informe(actual,todos)
        @sorpresa.aplicar_a_jugador(actual, todos)
      end
    end
    
    def to_s
      to_s="CasillaSorpresa Mazo #{@mazo.to_s}"
    end
  end
end
