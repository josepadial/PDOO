# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

require_relative "diario.rb"
require 'singleton'

module Civitas
class Dado
  
  include Singleton
  SALIDACARCEL = 5
  
  def initialize
    @ultimoResultado
    @debug = false
  end
  
  public 
  
  def tirar
    if(@debug == false)
      resultado = Random.rand(1..6)
      return 3
    else
      return 1
    end
  end
  
  def salgo_de_la_carcel
      if(tirar == SALIDACARCEL)
        return true
      else
        return false
      end
      
  end
  
  def quien_empieza(n)
    return Random.rand(1..n)
  end
  
  def set_debug(deb)
    @debug = deb
    diario = diario.instance
    diario.ocurre_evento("se ha cambiado el estado de debug a #{@debug}")
  end
  
  def get_ultimo_resultado
    @ultimoResultado
  end
  
  
end
end
