# encoding: utf-8
# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

require_relative "tipo_sorpresa.rb"
require_relative "mazo_sorpresas.rb"
require_relative "tablero.rb"
require_relative "diario.rb"
require_relative "jugador.rb"
require_relative "casilla.rb"

module Civitas
  class Sorpresa
    def self.ini_carcel(tipo,tablero)
      self.new(tipo)
      @tablero = tablero
      @texto = "Carcel"
    end
    
    def self.ini_mov_otra(tipo,tablero,valor,texto)
      self.new(tipo)
      @tablero = tablero
      @valor = valor
      @texto = texto
    end
    
    def self.ini_exit(tipo,mazo)
      self.new(tipo)
      @mazo = mazo
      @texto = "Evita la carcel"
    end
    
    def self.ini_resto(tipo,valor,texto)
      self.new(tipo)
      @valor = valor
      @texto = texto
    end
    
    def initialize(tipo)
      init()
      @tipo = tipo
      @diario = Diario.instance
    end
    
    # private_class_method :new
     
    private
    
    def aplicar_a_jugador_ir_a_casilla(actual,todos)
      if(jugador_correcto(actual,todos))
        informe(actual,todos)
        casilla_actual = todos.at(actual).num_casilla_actual
        tirada = @tablero.calcular_tirada(casilla_actual, @valor)
        nueva_pos = @tablero.nueva_posicion(casilla_actual, tirada)
        todos.at(actual).mover_a_casilla(nueva_pos)
        @tablero.get_casilla(nueva_pos).recibe_jugador(actual,todos)
      end
    end
    
    def aplicar_a_jugador_ir_carcel(actual,todos)
      if(jugador_correcto(actual,todos))
        informe(actual,todos)
        todos.at(actual).encarcelar(@tablero.num_casilla_carcel)
      end
    end
    
    def aplicar_a_jugador_pagar_cobrar(actual,todos)
      if(jugador_correcto(actual,todos))
        informe(actual,todos)
        todos.at(actual).modificar_saldo(@valor)
      end
    end
    
    def aplicar_a_jugador_por_casa_hotel(actual,todos)
      if(jugador_correcto(actual,todos))
        informe(actual,todos)
        todos.at(actual).modificar_saldo(@valor*todos.at(actual).cantidad_casas_hoteles())
      end
    end
    
    def aplicar_a_jugador_por_jugador(actual,todos)
      if(jugador_correcto(actual,todos))
        informe(actual,todos)
        aux = Sorepresa.ini_resto(TipoSorpresa::PAGARCOBRAR,valor*-1,"aux")
        aux2 = Sorepresa.ini_resto(TipoSorpresa::PAGARCOBRAR,valor*(todos.lenght),"aux2")
        for i in 1..todos.lenght
          if(i != actual)
            aux.aplicar_a_jugador(i,todos)
          else
            aux2.aplicar_a_jugador(actual,todos)
          end
        end
      end
    end
    
    def aplicar_a_jugador_salir_carcel(actual,todos)
      salida = false
      if(jugador_correcto(actual,todos))
        informe(actual,todos)
        for i in 1..todos.lenght
          if(i.tiene_salvoconducto() == true && i != actual)
            salida = true
          end
        end
        if(!salida)
          todos.at(actual).obtener_salvoconducto(self)
          salir_del_mazo()
        end
      end
      salida
    end
    
    def informe(actual,todos)
      diario.ocurre_evento("Se aplica la sorpresa " + @texto + " a " + todos.at(actual).nombre)
    end
    
    def init()
      @valor = -1
      @mazo = MazoSorpresas.new()
      @tablero = Tablero.new(10)
      @texto = ""
    end
    
    public
     
    def apliacar_a_jugador(actual,todos)
      case @tipo
        when TipoSorpresa::IRCASILLA
          aplicar_a_jugador_ir_a_casilla(actual,todos)
        when TipoSorpresa::IRCARCEL
          aplicar_a_jugador_ir_carcel(actual,todos)
        when TipoSorpresa::PAGARCOBRAR
          aplicar_a_jugador_pagar_cobrar(actual,todos)
        when TipoSorpresa::PORCASAHOTEL
          aplicar_a_jugador_por_casa_hotel(actual,todos)
        when TipoSorpresa::PORJUGADOR
          aplicar_a_jugador_por_jugador(actual,todos)
        when TipoSorpresa::SALIRCARCEL
          aplicar_a_jugador_salir_carcel(actual,todos)
      end
    end
    
    def jugador_correcto(actual,todos)
      todos.lenght > actual
    end
    
    def salir_del_mazo()
      if(@tipo == TipoSorpresa::SALIRCARCEL)
        @mazo.inhabilitar_carta_especial(self)
      end
    end
    
    def usadas()
      if(@tipo == TipoSorpresa::SALIRCARCEL)
        @mazo.habilitar_carta_especial(self)
      end
    end
    
    def to_s
      "Sorpresa{texto=#{@texto}, valor=#{@valor}, tipo=#{@tipo}}"
    end
    
  end
end
