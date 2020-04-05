# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

require_relative "vista_textual.rb"
require_relative "controlador.rb"
require_relative "dado.rb"
require_relative "civitas_juego.rb"

module JuegoTexto
  class JuegoTexto
    include Civitas
    def self.main
      @vista = Vista_textual.new
      
      @nombres = Array.new()
      @nombres << "Jose"
      @nombres << "Alex"
      @nombres << "Ruben"
      
      @@dado = Dado.instance 
      @@dado.debug = true
      
      @juego = CivitasJuego.new(@nombres)
      @controller = Controlador.new(@juego,@vista)
      @controller.juega()
    end
  end
  JuegoTexto.main
end
