# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

module Civitas
  class SorpresaConvertirme < Sorpresa
    def initialize(texto, fianza)
      @texto = texto
      @fianza = fianza
    end
    
    def aplicar_a_jugador(actual,todos)
      if(jugador_correcto(actual,todos))
        informe(actual,todos)
        especulador = todos[actual].convertieme(@fianza)
        todos[actual] = especulador
      end
    end
    
    def to_s
      to_s="Sorpresa convertirse {#{@texto}, fianza=#{@fianza}}"
    end
  end
end
