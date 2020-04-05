# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

module Civitas
  class JugadorEspeculador < Jugador
    @@FACTOR_ESPECULADOR = 2
    def initialize(otro, fianza)
      super(otro)
      @fianza = fianza
    end
    
    def puedo_edificar_casa(propiedad)
      puede = false
      
      for i in @propiedades
        if(propiedad == @propiedades[i] && @propiedades[i].get_num_casas < @@CASAS_MAX*@@FACTOR_ESPECULADOR)
          puede = true
        end
      end
      puede
    end
    
    def puedo_edificar_hotel(propiedad)
      puede = false
      
      for i in @propiedades
        if(propiedad == @propiedades[i] && @propiedades[i].get_num_hoteles < @@HOTELES_MAX*@@FACTOR_ESPECULADOR)
          puede = true
        end
      end
      puede
    end
    
    def paga_impuesto(cantidad)
      if(@encarcelado == true)
          return false
      else
          return paga(cantidad/2)
      end  
    end
    
    def encarcelar(num_casilla_carcel)
      if(debe_ser_encarcelado() == true && !paga(@@PRECIO_LIBERTAD))
            mover_a_casilla(num_casilla_carcel)
            @encarcelado = true
            @diario.ocurre_evento("Jugador " + @nombre + " encarcelado")
            return @encarcelado
      else
            return @encarcelado
      end
    end
    
    def to_s
      to_s = "Jugador Especulador Fianza #{@fianza} #{super}"
    end
  end
end
