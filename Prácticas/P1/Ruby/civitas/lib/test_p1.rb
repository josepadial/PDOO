# encoding: utf-8
# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

require_relative "dado.rb"
require_relative "mazo_sorpresas.rb"

module Civitas
  class TestP1
    NUM_JUGADORES = 4
    @@dado = Dado.instance
    @@mazo = MazoSorpresas.new
    def self.main
      @x=100
      @uno = 0
      @dos = 0
      @tres = 0
      @cuatro = 0
      for i in 1..@x do
        @valor = @@dado.quien_empieza(NUM_JUGADORES)
        case @valor
        when 1
          @uno = @uno + 1
        when 2
          @dos = @dos + 1
        when 3
          @tres = @tres + 1
        when 4
          @cuatro = @cuatro + 1 
        end
      end
      puts "\t\tValores de quien empieza\n"
      puts "Uno: " + @uno.to_s + "\n"
      puts "Dos: " + @dos.to_s + "\n"
      puts "Tres: " + @tres.to_s + "\n"
      puts "Cuatro: " + @cuatro.to_s + "\n"
      
      puts "\t\tValor de dado debug = off\n"
      @@dado.debug = false
      puts "Tirada uno: " + @@dado.tirar.to_s + "\n"
      puts "Tirada dos: " + @@dado.tirar.to_s + "\n"
      
      puts "\t\tValor de dado debug = on\n"
      @@dado.debug = true
      puts "Tirada uno: " + @@dado.tirar.to_s + "\n"
      puts "Tirada dos: " + @@dado.tirar.to_s + "\n"
      
      puts "\t\tUltimo resultado de DADO\n"
      @@dado.debug = false
      puts "Tirada uno: " + @@dado.tirar.to_s + "\n"
      puts "Resultado almacenado: " + @@dado.ultimo_resultado.to_s + "\n"
      puts "Tirada dos: " + @@dado.tirar.to_s + "\n"
      puts "Resultado almacenado: " + @@dado.ultimo_resultado.to_s + "\n"
      
      puts "\t\tSalgo de la carcel DADO\n"
      puts "Tirada uno: " + @@dado.salgo_de_la_carcel.to_s + "  " + @@dado.ultimo_resultado.to_s + "\n"
      puts "Tirada dos: " + @@dado.salgo_de_la_carcel.to_s + "  " + @@dado.ultimo_resultado.to_s + "\n"
      puts "Tirada tres: " + @@dado.salgo_de_la_carcel.to_s + "  " + @@dado.ultimo_resultado.to_s + "\n"
      
      @a = Sorpresa.new
      @b = Sorpresa.new
      @@mazo.al_mazo(@a)
      @@mazo.al_mazo(@b)
      
      @@mazo.siguiente
      
      @@mazo.inhabilitar_carta_especial(@b)
      @@mazo.habilitar_carta_especial(@b)
    end
  end
  TestP1.main
end
