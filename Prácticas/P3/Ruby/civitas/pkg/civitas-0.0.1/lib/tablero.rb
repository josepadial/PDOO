# encoding: utf-8
# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

require_relative "casilla.rb"

module Civitas
  class Tablero
    attr_reader :num_casilla_carcel, :por_salida
    def initialize(num_casilla_carcel)
      @num_casilla_carcel = num_casilla_carcel
      @casillas = Array.new
      @por_salida = 0
      @tiene_juez = false
    end
    
    private
    /def correcto()
      (@casillas.length > @num_casilla_carcel && @tiene_juez == true)
    end/
    
    def correcto(num_casilla = @num_casilla_carcel)
      (@casillas.length > @num_casilla_carcel && @tiene_juez == true && @casillas.length > num_casilla)
    end
    
    public
    
    def añade_casilla(casilla)
      if @casillas.length == @num_casilla_carcel
        @casillas << Casilla.ini_descanso("Carcel")
      end
      @casillas << casilla
      if @casillas.length == @num_casilla_carcel
        @casillas << Casilla.ini_descanso("Carcel")
      end
    end
    
    def añade_juez()
      if @tiene_juez == false
        @casillas << Casilla.ini_juez(10,"Juez")
        @tiene_juez = true
      end
    end
    
    def get_casilla(num_casilla)
      casilla = @casillas.at(num_casilla) if correcto(num_casilla)
      casilla
    end
    
    def nueva_posicion(actual,tirada)
      nueva_pos = 0
      if !correcto()
        nueva_pos = -1
      else
        nueva_pos = (actual+tirada)%@casillas.length
        if actual > nueva_pos
          @por_salida = @por_salida + 1
        end
      end
      nueva_pos
    end
    
    def calcular_tirada(origen,destino)
      valor = origen - destino
      
      if valor < 0
        valor = valor + @casillas.length
      end
      valor
    end
  end
end
