# encoding: utf-8
# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

require "singleton"

module Civitas
  class Dado
    include Singleton
    SALIDA_CARCEL = 5
    
    attr_writer :debug
    attr_reader :ultimo_resultado
    def initialize
      @ultimo_resultado
      @debug = false
    end
    
    def tirar
      if @debug == false
        @ultimo_resultado = Random.rand(1..6)
      else
        @ultimo_resultado = 1
      end
      @ultimo_resultado
    end
    
    def salgo_de_la_carcel
      tirar() == SALIDA_CARCEL
    end
    
    def quien_empieza(n)
      Random.rand(1..n)
    end
  end
end
