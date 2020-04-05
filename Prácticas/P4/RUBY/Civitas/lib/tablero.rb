# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.


require_relative"casilla.rb"
require_relative"diario.rb"


module Civitas
  class Tablero
  
    def initialize(num)
      if(num>1)
        @numCasillaCarcel = num
      else
        @numCasillaCarcel = 1
      end
      
      @casillas = Array.new
      @casillas << Casilla.new("Salida")
      @porSalida = 0
      @tieneJuez = false
    end
    
    def aniade_casilla_salida
      @casillas << Casilla.init_descanso("Salida")
    end
    
    def correctoSA
      if(@casillas.length > @numCasillaCarcel && @tieneJuez == true)
        return true
      else
        return false
      end
    end
    
    def correcto(numCasilla)
      if(correctoSA == true && numCasilla <= @casillas.length)
        return true
      else
        return false
      end
    end
    
    def get_carcel
      @numCasillaCarcel
    end

    def get_por_salida
      @porSalida
    end

    def aniade_casilla(casilla)
      if(@casillas.length == @numCasillaCarcel)
        @casillas << Casilla.init_descanso("Carcel")
      end
      
      @casillas << casilla
      
      if(@casillas.length == @numCasillaCarcel)
        @casillas << Casilla.new("Carcel")
      end
      
    end
    
    def aniade_juez
      if(@tieneJuez == false)
        CasillaJuez.new("Juez",@numCasillaCarcel)
        @tieneJuez = true
      end
    end
    
    def get_casilla(num)
      if(num <= @casillas.length)
        return @casillas.at(num)
      else
        return nil
      end
    end
    
    def nueva_posicion(actual,tirada)
      if(correctoSA == false)
        return -1
      else
        
        pos = actual + tirada
        
        if(pos >= @casillas.length)
          @porSalida = @porSalida + 1
          newpos = pos - @casillas.length
          return newpos
        else
          return pos
        end
        
      end
    end
    
    def calcular_tirada(origen,destino)
      tirada = destino - origen
      
      if(tirada <= 0)
        newtirada = tirada + @casillas.length
        return newtirada
      else
        return tirada
      end
    end

    def to_s
       to_s = "Tablero ( casillas = #{@casillas} carcel = #{@numCasillaCarcel}"
    end
    
  end
end