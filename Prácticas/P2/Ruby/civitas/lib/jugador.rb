# encoding: utf-8
# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

require_relative "dado.rb"
require_relative "diario.rb"
require_relative "titulo_propiedad.rb"
require_relative "sorpresa.rb"

module Civitas
  class Jugador
    attr_reader :propiedades, :casas_max, :casas_por_hotel, :encarcelado, :hoteles_max, :nombre, :num_casilla_actual, :precio_libertad, :puede_comprar, :saldo, :paso_por_salida
    @@CASAS_MAX = 4
    @@CASAS_POR_HOTEL = 4
    @@HOTELES_MAX = 4
    @@PASO_POR_SALIDA = 1000
    @@PRECIO_LIBERTAD = 200
    @@SALDO_INICIAL = 7500
    
    def initialize(nombre)
      @nombre = nombre
      @encarcelado = false
      @propiedades = Array.new
      @saldo = @@SALDO_INICIAL
      @puede_comprar = false
      @diario = Diario.instance
      @dado = Dado.instance
      @num_casilla_actual = 0
      @salvoconducto = nil
    end
    
    def self.ini_otro(otro)
      self.new(otro.nombre)
      @encarcelado = otro.encarcelado
      @propiedades = otro.propiedades
      @saldo = otro.saldo
      @puede_comprar = otro.puede_comprar
      @num_casilla_actual = otro.num_casilla_actual
    end
    
    private
    
    def debe_ser_encarcelado
      salida = false
      if(!@encarcelado)
        if(!tiene_salvoconducto)
          salida = true
        else
          perder_salvoconducto()
          @diario.ocurre_evento("Se libra de la carcel el jugador: " + @nombre)
        end
      end
      salida
    end
    
    def perder_salvo_conducto()
      @salvoconducto.usadas()
      @salvoconducto = nil
    end
    
    def puede_salir_carcel_pagando()
      @saldo >= @@PRECIO_LIBERTAD
    end
    
    def puede_edificar_casa(propiedad)
      (puedo_gastar(propiedad.precio_edificar) && propiedad.num_casas < 4)
    end
    
    def puede_edificar_hotel(propiedad)
      (puedo_gastar(propiedad.precio_edificar) && propiedad.num_hoteles < 4 && propiedad.num_casas == 4)
    end
    
    def puedo_gastar(precio)
      !@encarcelado && @saldo >= precio
    end
    
    public
    
    def cancalar_hipoteca(ip)
      
    end
    
    def cantidad_casas_hoteles()
      suma = 0
      for i in 1..@propiedades.lenght
        suma = suma + @propiedades.at(i).num_casas + @propiedades.at(i).num_hoteles
      end
      suma
    end
    
    def <=>(otroJugador)
      otroJugador.obtener_capital <=> obtener_capital
    end
    
    def comprar(titulo)
      
    end
    
    def construir_casa(ip)
      
    end
    
    def construir_hotel(ip)
      
    end
    
    def en_bancarrota()
      @saldo <= 0
    end
    
    def encarcelar(num_casilla_carcel)
      if(debe_ser_encarcelado())
        mover_a_casilla(num_casilla_carcel)
        @encarcelado = true
        @diario.ocurre_evento("Se encarcela al jugador:  " + @nombre)
      end
      @encarcelado
    end
    
    def existe_la_propiedad(ip)
      @propiedades.include?(@propiedades.at(ip))
    end
    
    def hipotecar(ip)
      
    end
    
    def modificar_saldo(cantidad)
      @saldo = @saldo + cantidad
      @diario.ocurre_evento("Se ha modificado el saldo en: " + cantidad);
      true
    end
    
    def mover_a_casilla(num_casilla)
      salida = false
      if !@encarcelado
        @num_casilla_actual = num_casilla
        @puede_comprar = false
        @diario.ocurre_evento("Jugador movido a la casilla:  #{@nombre}")
        salida = true
      end
      salida
    end
    
    def obtener_salvoconducto(sorpresa)
      salida = false
      if !@encarcelado
        @salvoconducto = sorpresa
        salida = true
      end
      salida
    end
    
    def paga(cantidad)
      modificar_saldo(cantidad*-1)
    end
    
    def paga_alquiler(cantidad)
      salida = false
      if !@encarcelado
        salida = paga(cantidad)
      end
      salida
    end
    
    def paga_impuesto(cantidad)
      salida = false
      if !@encarcelado
        salida = paga(cantidad)
      end
      salida
    end
    
    def pasa_por_salida()
      modificar_saldo(@@PASO_POR_SALIDA)
      @diario.ocurre_evento("Jugador pasa por la salida:  " + @nombre)
      true
    end
    
    def puede_comprar_casilla()
      if @encalcelado
        @puede_comprar = false
      else
        @puede_comprar = treu
      end
      @puede_comprar
    end
     
    
    def recibe(cantidad)
      salida = false
      if !@encarcelado
        salida = modificar_saldo(cantidad)
      end
      salida
    end
    
    def salir_carcel_pagando()
      salida = false
      if @encarcelado && puede_salir_carcel_pagando()
        paga(@@PRECIO_LIBERTAD)
        @encarcelado = false
        salida = true
        @diario.ocurre_evento("Jugador sale del la carcel")
      end
      salida
    end
    
    def salir_carcel_tirando()
      salida = @dado.salgo_de_la_carcel
      if salida
        @encarcelado = false
        @diario.ocurre_evento("Jugador sale del la carcel")
      end
      salida
    end
    
    def tiene_algo_que_gestionar()
      @propiedades.empty?
    end
    
    def tiene_salvoconducto()
      @salvoconducto != nil
    end
    
    def vender(ip)
      salida = false
      if !@encacelado && existe_la_propiedad(ip)
        salida = @propiedades.at(ip).vender(self)
        @propiedades.delete_at(ip)
        @diario.ocurre_evento("Jugador vende")
      end
      salida
    end
    
    def to_s
      to_s = "Nombre: #{@nombre}\nSaldo: #{@saldo}\nEncarcelado: #{@encarcelado}\n#{@num_casilla_actual}\n"
      
      if !@propiedades.empty?
        for p in @propiedades
          to_s += "#{p}"
        end
      end
      to_s
    end
    
  end
end