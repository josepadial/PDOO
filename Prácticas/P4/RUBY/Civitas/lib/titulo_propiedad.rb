# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

require_relative"jugador.rb"
require_relative"diario.rb"

module Civitas
  
class Titulo_propiedad
  
  @@FACTOR_INTERESES_HIPOTECA = 1.1
  
  def initialize(nombre,ab,fr,hb,pc,pe)
    @nombre = nombre
    @alquiler_base = ab
    @factor_revalorizacion = fr
    @hipoteca_base = hb
    @precio_compra = pc
    @precio_edificar = pe
    @hipotecado = false
    @num_casas = 0
    @num_hoteles = 0
    @propietario = nil
  end
  
  def actualiza_propietario_por_conversion(jugador)
    @propietario = jugador
  end
  
  def cancelar_hipoteca(jugador)
    resultado = false
    if(@hipotecado == true && es_este_el_propietario(jugador) == true)
      jugador.paga(get_importe_cancelar_hipoteca)
      resultado = true
      @hipotecado = false
    end
        return resultado
  end
  
  def cantidad_casas_hoteles
    return (@num_casas + @num_hoteles)
  end

  def comprar(jugador)
    resultado = false
    if(@propietario == nil)
      @propietario = jugador
      resultado = true
      jugador.paga(@precio_compra)
    end
        return resultado
  end

  def construir_casa(jugador)
    resultado = false
    if(es_este_el_propietario(jugador) == true)
        jugador.paga(@precio_edificar)
        @num_casas = @num_casas+1
        resultado = true
    end
        return resultado
  end
  
  def construir_hotel(jugador)
    resultado = false
    if(es_este_el_propietario(jugador) == true)
        jugador.paga(@precio_edificar)
        @num_hoteles = @num_hoteles + 1
        resultado = true
    end
        return resultado
  end

  def derruir_casas(n,jugador)
    if(jugador == @propietario && @num_casas >= n)
        @num_casas = @num_casas - n
        return true
    else
        return false
    end 
  end
  
  def get_hipotecado
    @hipotecado
  end
  
  def get_importe_cancelar_hipoteca
    return (@hipoteca_base * @@FACTOR_INTERESES_HIPOTECA)
  end
  
  def get_nombre
    @nombre
  end
  
  def get_num_casas
    @num_casas
  end
  
  def get_num_hoteles
    @num_hoteles
  end
 
  def get_precio_compra
    @precio_compra
  end
  
  def get_precio_edificar
    @precio_edificar
  end
  
  def get_propietario
    @propietario
  end
  
  def hipotecar(jugador)
     salida = false
     if(@hipotecado == false && es_este_el_propietario(jugador)==true)
         jugador.recibe(get_importe_hipoteca)
         @hipotecado = true
         salida = true
     end
        return salida
  end
  
  def tiene_propietario
    if(@propietario != nil)
        return true
    else
        return false
    end   
  end
  
  def tramitar_alquiler(jugador)
    if(@propietario != nil && jugador != @propietario)
        jugador.paga(get_precio_alquiler)
        @propietario.recibe(get_precio_alquiler)
    end
  end
  
  def vender(jugador)
    if(jugador == @propietario && @hipotecado == false)
        jugador.recibe(get_precio_venta)
        @propietario = nil
        @num_casas = 0
        @num_hoteles = 0
        return true
    else
        return false
    end
  end
  
  private
  def propietario_encarcelado
    if(@propietario.get_encarcelado == true)
      return true
    else
      return false
    end
  end
  
  def get_precio_venta
     precioHoteles = @num_hoteles * @precio_edificar
     precioCasas = @num_casas * @precio_edificar
     importe = (precioCasas + precioHoteles) * @factor_revalorizacion
     importe = @precio_compra + importe
     return importe
  end
  
  def get_precio_alquiler
    if(@hipotecado == true || propietario_encarcelado == true)
      return 0
    else
      @alquiler_base
    end
  end
  
  def get_importe_hipoteca
    @hipoteca_base
  end
  
  def es_este_el_propietario(jugador)
    if(jugador == @propietario)
        return true
    else
        return false
    end  
  end
  
  public
  def to_s
      to_s = "Nombre: #{@nombre} Hipotecada: #{@hipotecada} Precio de compra: #{@precio_compra} Alquiler base: #{@alquiler_base} Factor de revalorizacion: #{@factor_revalorizacion}% Hipoteca base: #{@hipoteca_base} Precio de edificacion: #{@precio_edificar} Numero de casas: #{@num_casas} Numero de hoteles: #{@num_hoteles}\n"
  end
  
end

end
