# encoding: utf-8
# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

require_relative "tipo_casilla.rb"
require_relative "diario.rb"
require_relative "titulo_propiedad.rb"
require_relative "sorpresa.rb"
require_relative "jugador.rb"
require_relative "mazo_sorpresas.rb"

module Civitas
  class Casilla
    attr_reader :nombre, :titulo_propiedad
    
    def initialize()
      init()
      @diario = Diario.instance
    end
    
    def self.ini_descanso(cadena)
      self.new()
      @nombre = cadena
      @tipo = TipoCasilla::DESCANSO
    end
    
    def self.ini_calle(titulo)
      self.new()
      @titulo_propiedad = titulo
      @nombre = titulo.nombre
      @tipo = TipoCasilla::CALLE
      @importe = titulo.precio_compra
    end
    
    def self.ini_impuesto(cantidad, nombre)
      self.new()
      @importe = cantidad
      @nombre = nombre
      @tipo = TipoCasilla::IMPUESTO
    end
    
    def self.ini_juez(num_casilla_carcel,nombre)
      self.new()
      @@carcel = num_casilla_carcel
      @nombre = nombre
      @tipo = TipoCasilla::JUEZ
    end
    
    def self.ini_sorpresa(mazo,nombre)
      self.new()
      @mazo = mazo
      @nombre = nombre
      @tipo = TipoCasilla::SORPRESA
    end
    
    #private_class_method :new
    
    private
    
    def informe(iactual, todos)
      @diario.ocurre_evento("En la casilla " + to_s() + " esta " + todos[iactual].to_s())
    end
    
    def init()
      @nombre = ""
      @importe = 0
      @@carcel = -1
      @tipo = TipoCasilla::DESCANSO
      @titulo_propiedad = nil
      @sorpresa = nil
      @mazo = nil
    end
    
    def recibe_jugador_calle(iactual,todos)
      if(jugador_correcto(iactual,todos))
        informe(iactual,todos)
        nuevo = Jugador.new(todos.at(iactual))
        if(!@titulo_propiedad.tiene_propietario)
          nuevo.puede_comprar_casilla
        else
          @titulo_propiedad.tramitar_alquiler(nuevo)
        end
      end
    end
    
    def recibe_jugador_impuestos(iactual,todos)
      if(jugador_correcto(iactual,todos))
        informe(iactual, todos)
        todos.at(iactual).paga_impuestos(@importe)
      end
    end
    
    def recibe_jugador_juez(iactual,todos)
      if(jugador_correcto(iactual,todos))
        informe(iactual, todos)
        todos.at(iactual).encarcelar(@@carcel)
      end
    end
    
    def recibe_jugador_sorpresa(iactual,todos)
      sorpresa = @mazo.siguiente
      informe(iactual, todos)
      sorpresa.apliacar_a_jugador(iactual,todos)
    end
    
    public
    
    def jugador_correcto(iactual,todos)
      todos.lenght > iactual
    end
    
    def recibe_jugador(iactual,todos)
      case @tipo
        when TipoCasilla::CALLE
          recibe_jugador_calle(iactual,todos)
        when TipoCasilla::IMPUESTO
          recibe_jugador_impuestos(iactual,todos)
        when TipoCasilla::JUEZ
          recibe_jugador_juez(iactual,todos)
        when TipoCasilla::SORPRESA
          recibe_jugador_sorpresa(iactual,todos)
        else
          informe(iactual,todos)
      end
    end
    
    
    def to_s
      "Nombre #{@nombre}"
    end
    
  end
end