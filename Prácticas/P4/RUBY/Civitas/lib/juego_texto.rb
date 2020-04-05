# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

require_relative"dado.rb"
require_relative"civitas_juego.rb"
require_relative"vista_textual.rb"
require_relative"controlador.rb"

module Civitas
class Juego_texto
  
  def self.get_nombre_jugadores
      
      nombres = []
      cont = 0
      puts "Introduzca el numero de jugadores"
      numero = gets.chomp.to_i
      
      if(numero >= 2 and numero <= 4)
        while cont < numero
          puts "Introduzca nombre de jugador"
          cadena = gets
          nombres<< cadena
          puts "Jugador creado"
          cont = cont + 1
        end
      else puts "El numero de jugadores no es correcto"      
      end
        nombres
    end
  
  def self.main()
    
    nombres = get_nombre_jugadores
    @@juego = Civitas_juego.new(nombres)
    
    vista = Vista_textual.new
    controlador = Controlador.new(@@juego,vista)
    controlador.juega()
    
  end
  
  
end

Juego_texto.main
end