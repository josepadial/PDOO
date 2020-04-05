# encoding: utf-8
# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

require_relative "diario.rb"
require_relative "sorpresa.rb"

module Civitas
  class MazoSorpresas
    def initialize(debug = false)
      init
      @debug = debug
      @ultima_sorpresa
      @diario = Diario.instance
      if @debug == true
        @diario.ocurre_evento("MazoSorpresas en modo debug")
      end
    end
    #private_class_method :new
    
    private
    def init
      @sorpresas = Array.new
      @barajada = false
      @usadas = 0
      @cartas_especiales = Array.new
    end
    
    public
    def al_mazo(s)
      if(@barajada == false)
        @sorpresas << s
      end
    end
    
    def siguiente
      if @barajada == false && @usadas == @sorpresas.length && @debug == false
        @sorpresas.shuffle
        @usadas = 0;
        @barajada = true;
      end
      @usadas = @usadas + 1
      @ultima_sorpresa = @sorpresas.first
      @sorpresas.shift
      @sorpresas << @ultima_sorpresa
      @ultima_sorepresa
    end
    
    def inhabilitar_carta_especial(sorpresa)
      if @sorpresas.index(sorpresa) != nil
        @cartas_especiales << @sorpresas[@sorpresas.index(sorpresa)]
        @sorpresas.delete(sorpresa)
        @diario.ocurre_evento("Carta especial inhabilitada")
      end
    end
    
    def habilitar_carta_especial(sorpresa)
      if @cartas_especiales.index(sorpresa) != nil
        @sorpresas << @cartas_especiales[@cartas_especiales.index(sorpresa)]
        @cartas_especiales.delete(sorpresa)
        @diario.ocurre_evento("Carta especial habilitada")
      end
    end
  end
end
