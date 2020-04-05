# encoding: utf-8
# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

require_relative "jugador.rb"

module Civitas
  class TituloPropiedad
    attr_reader :propietario, :hipotecado, :nombre, :num_casas, :num_hoteles, :precio_compra, :precio_edificar
    @@factor_interes_hipoteca = 1.1
    def initialize(nom,ab,fr,hb,pc,pe)
      @nombre = nom
      @alquiler_base = ab
      @factor_revalorizacion = fr
      @hipoteca_base = hb
      @precio_compra = pc
      @precio_edificar = pe
      @propietario = nil
      @num_casas = 0
      @num_hoteles = 0
      @hipotecado = false
    end
    
    private
    def es_este_el_propietario(jugador)
      @propietario == jugador
    end
    
    def get_precio_alquiler()
      if(@hipotecado || propietario_encarcelado())
        salida = 0
      else
        salida = @alquiler_base*(1+(@num_casas*0.5)+(@num_hoteles*2.5))
      end
      salida
    end
    
    def get_importe_hipotecada()
      @hipoteca_base*(1+(@num_casas*0.5)+(@num_hoteles*2.5))
    end
    
    def get_precio_venta()
      @precio_compra+(@num_casas+5*@num_hoteles)*@precio_edificar*@factor_revalorizacion
    end
    
    def propietario_encarcelado()
      @propietario.encarcelado
    end
    
    public

    def actualizar_propietario_por_conversion(jugador)
      @propietario = jugador
    end
    
    def cancelar_hipoteca(jugador)
      salida = false
      if(@hipotcado && es_este_el_propietario(jugador))
        if(@propietario.paga(get_importe_cancelar_hipoteca()))
          @hipotecado = false
          salida = true
        end
      end
      salida
    end
    
    def cantidad_casas_hoteles()
      (@num_casas + @num_hoteles)
    end
    
    def comprar(jugador)
      salida = false
      if(!tiene_propietario())
        @propietario = jugador
        @propietario.paga(@precio_compra)
        salida = true
      end
      salida
    end
    
    def construir_casa(jugador)
      salida = false
      if(es_este_el_propietario(jugador) && @num_casas < 4 && jugador.saldo > @precio_edificar)
        jugador.paga(@precio_edificar)
        @num_casas = @num_casas+1
        salida = true
      end
      salida
    end
    
    def construir_hotel(jugador)
      salida = false
      if(es_este_el_propietario(jugador) && @num_hoteler < 4 && @num_casas == 4 && jugador.saldo > @precio_edificar)
        jugador.paga(@precio_edificar)
        @num_casas = @num_casas-4
        @num_hoteles = @num_hoteles +1
        salida = true
      end
      salida
    end
    
    def derruir_casas(n,jugador)
      salida = false
      if(es_este_el_propietario(jugador) && @num_casas >= n)
        @num_casas = @num_casas-n
        salida = true
      end
      salida
    end
    
    def get_importe_cancelar_hipoteca()
      get_importe_hipotecada()*@@factor_interes_hipoteca
    end
    
    def hipotecar(jugador)
      salida = false
      if !@hipotecado && es_este_el_propietario(jugador)
        if @propietario.recibe(get_importe_cancelar_hipoteca())
          @hipotecado = true
          salida = true
        end
      end
      salida
    end
    
    def tiene_propietario()
      @propietario != nil
    end
    
    def tramitar_alquiler(jugador)
      if(tiene_propietario() && !es_este_el_propietario(jugador))
        jugador.paga_alquiler(get_precio_alquiler())
        @propietario.recibe(get_precio_alquiler())
      end
    end
    
    def vender(jugador)
      salida = false
      if(es_este_el_propietario(jugador) && !@hipotecado)
        jugador.recibe(get_precio_venta())
        @propietario = nil
        @num_casas = 0
        @num_hoteels = 0
        salida = true
      end
      salida
    end
    
     def to_s
      to_s = "Nombre: #{@nombre}\nHipotecado: #{@hipotecado}\nPrecio de compra: #{@precio_compra}\n"
      to_s+= "Alquiler base: #{@alquiler_base}\nFactor de revalorización: #{@factor_revalorizacion}%\n"
      to_s+= "Hipoteca base: #{@hipoteca_base}\nPrecio de edificación: #{@precio_edificar}\n"
      to_s+= "Número de casas: #{@num_casas}\nNúmero de hoteles: #{@num_hoteles}\n"
    end
    
  end
end