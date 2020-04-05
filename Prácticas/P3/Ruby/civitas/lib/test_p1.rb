# encoding: utf-8
# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

require_relative "dado.rb"
require_relative "mazo_sorpresas.rb"
require_relative "civitas_juego.rb"
require_relative "casilla.rb"
require_relative "jugador.rb"
require_relative "titulo_propiedad.rb"

module Civitas
  class TestP1
    NUM_JUGADORES = 4
    @@dado = Dado.instance
    @@mazo = MazoSorpresas.new
    
    def self.get_nombre_jugadores()
      nombres = Array.new
      nombre = ""
      
      for i in 1..NUM_JUGADORES
        print "Nombre del jugador #{i}: "
        nombre = gets.chomp
        nombres << nombre
      end
      nombres
    end
    
    def self.main
      @nombres = get_nombre_jugadores()
      @juego = CivitasJuego.new(@nombres)
      
      @juego.get_jugador_actual.mover_a_casilla(@@dado.tirar + @juego.get_jugador_actual.num_casilla_actual)
      print "Valor del dado #{@@dado.ultimo_resultado}"
      
      #@juego.get_jugador_actual.comprar(@juego.get_casilla_actual.titulo_propiedad)
      #@juego.construir_casa(8)
      
      print "#{@juego.info_jugador_texto}"
      print "#{@juego.get_casilla_actual.to_s}"
      
      @juego.get_jugador_actual.mover_a_casilla(@@dado.tirar+@juego.get_jugador_actual.num_casilla_actual)
      print "Valor del dado #{@@dado.ultimo_resultado}"
      
      print "#{@juego.info_jugador_texto}"
      print "#{@juego.get_casilla_actual.to_s}"
    end
  end
  TestP1.main
end
