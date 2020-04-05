# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

require_relative "sorpresa.rb"
require_relative "diario.rb"

module Civitas
class Mazo_sorpresas
  
  def initialize(deb)
    if(deb == true)
      @debug = deb
    else
      @debug = false
    end
  
    @ultimaSorpresa = nil
    @sorpresas = Array.new
    @cartasEspeciales = Array.new
    @barajada = false
    @usadas = 0
  end
  
  public
  def al_mazo(s)
    if(@barajada == false)
      @sorpresas << s
    end
  end
  
  def siguiente
    if(@barajada == false || @usadas == @sorpresas.length || @debug == false)
      @sorpresas.shuffle
      @barajada = true
      @usadas = 0
    end
    
    @usadas = @usadas + 1
    @ultimaSorpresa = @sorpresas.first
    @sorpresas.shift
    @sorpresas<<@ultimaSorpresa
    
    @ultimaSorpresa
  end
  
  def inhabilitar_carta_especial(sorpresa)
    
    for i in 0..@sorpresas.length do
      if(sorpresa == @sorpresas[i])
        @cartasEspeciales << @sorpresas[i]
        @sorpresas.drop(i)
        diario = Diario.instance
        diario.ocurre_evento("Se ha inhabilitado la carta sorpresa")
      end
    end
    
  end
  
  def habilitar_carta_especial(sorpresa)
    
    for i in 0..@sorpresas.length do
      if(sorpresa == @sorpresas[i])
        @sorpresas << @cartasEspeciales[i]
        @cartasEspeciales.drop(i)
        diario = diario.instance
        diario.ocurre_evento("Se ha habilitado la carta sorpresa")
      end
    end
    
  end
  
  public
  def get_ultima_sorpresa
    return @ultimaSorpresa
  end
  
  def to_s
    to_s = "Sorpresas #{@sorpresas}"
  end
  
end
end
