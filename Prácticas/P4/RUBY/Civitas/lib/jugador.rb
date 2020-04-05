# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

require_relative"titulo_propiedad.rb"
require_relative"sorpresa.rb"
require_relative"diario.rb"

module Civitas
class Jugador
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
      @saldo = 7500
      @puede_comprar = false
      @diario = Diario.instance
      @dado = Dado.instance
      @num_casilla_actual = 0
      @salvo_conducto = nil
    end
    
    def self.init_otro(otro)
      temporal = new(otro.get_nombre)
      temporal.copia(otro)
      return temporal
    end
    
    def copia(otro)
      @encarcelado = otro.get_encarcelado
      @propiedades = otro.get_propiedades
      @saldo = otro.get_saldo
      @puede_comprar = otro.get_puede_comprar
      @num_casilla_actual = otro.get_num_casilla_actual
      @salvo_conducto = otro.get_salvoconducto
    end
    
    def convertirme(fianza)
      especulador = JugadorEspeculador.new(self, fianza)
      for i in @propiedades
        i.actualiza_propietario_por_conversion(especualdor)
      end
      especulador
    end
    
    def get_encarcelado
      return @encarcelado
    end
    
    def cadena_propiedades
      propiedades = Array.new
      
      for i in @propiedades
        propiedades << i.get_nombre
      end
      
      return propiedades
    end
    
    def cancelar_hipoteca(ip)
      resultado = false
        
        if(@encarcelado == true)
            return resultado
        else
            if(existe_la_propiedad(ip)==true)
                propiedad = @propiedades.at(ip)
                cantidad = propiedad.get_importe_cancelar_hipoteca
                
                if(puedo_gastar(cantidad) == true)
                    resultado = propiedad.cancelar_hipoteca(self)
                end
                
                if(resultado == true)
                    @diario.ocurre_evento("El jugador "+@nombre+ " cancela la hipoteca de la propiedad "+ip.to_s)
                end
            end
            return resultado
        end
    end
    
    def cantidad_casas_hoteles
      num_casas = 0
      num_hoteles = 0
      
      for i in @propiedades
        num_casas = num_casas + i.get_num_casas
        num_hoteles = num_hoteles + i.get_num_hoteles
      end
      
      return num_casas + num_hoteles
      
    end
    
    def comprar(titulo)
      resultado = false
        
        if(@encarcelado == true)
            return resultado
        end
        
        if(@puede_comprar == true)
            cantidad = titulo.get_precio_compra
            if(puedo_gastar(cantidad) == true)
                resultado = titulo.comprar(self)
                
                if(resultado == true)
                   @propiedades<<titulo
                   @diario.ocurre_evento("El jugador"+@nombre+ " compra la propiedad "+titulo.to_s)
                end
                
                @puede_comprar = false
            end
        end
        return resultado
    end
    
    def construir_casa(ip)
      resultado = false
        
        if(@encarcelado == true)
            return resultado
        end
        
        if(existe_la_propiedad(ip) == true)
            propiedad = @propiedades.at(ip)
            puedoEdificarCasa = puedo_edificar_casa(propiedad)
            puede = false
            precio = propiedad.get_precio_edificar
            
            if(puedo_gastar(precio) == true && puedoEdificarCasa == true)
                puede = true
            end
            
            if(puede == true)
                resultado = propiedad.construir_casa(self)
            end
            
            @diario.ocurre_evento("El jugador "+@nombre+ " construye casa en la propiedad "+ip.to_s)
            
        end
        
        return resultado
    end
    
    def construir_hotel(ip)
      resultado = false
        if(@encarcelado == true)
            return resultado
        end
        
        if(existe_la_propiedad(ip) == true)
            propiedad = @propiedades.at(ip)
            puedoEdificarHotel = puedo_edificar_hotel(propiedad)
            puede = false
            precio = propiedad.get_precio_edificar
            
            if(puedo_gastar(precio) == true && puedoEdificarHotel == true)
                puede = true
            end
            
            if(puede == true)
                resultado = propiedad.construir_hotel(self)
                propiedad.derruir_casas(@@CASAS_POR_HOTEL,self)
            end
            
            @diario.ocurre_evento("El jugador "+@nombre+ " construye hotel en la propiedad "+ip.to_s)
        end
        
        return resultado
    end
    
    def debe_ser_encarcelado
      debe = false
      if(@encarcelado == true)
            debe = false
      else
          if(tiene_salvo_conducto() == false)
              debe = true
          else
                perder_salvo_conducto();
                @diario.ocurreEvento("El jugador se libera de la carcel");
                @encarcelado = false
          end
      end
        return debe
    end
    
    def en_bancarrota
      if(@saldo < 0)
        return true
      else
        return false
      end
    end
    
    def encarcelar(num_casilla_carcel)
      if(debe_ser_encarcelado() == true)
            mover_a_casilla(num_casilla_carcel)
            @encarcelado = true
            @diario.ocurre_evento("Jugador " + @nombre + " encarcelado")
            return @encarcelado
      else
            return @encarcelado
      end
    end
    
    def get_casas_por_hoteles
      @@CASAS_POR_HOTEL
    end
    
    def get_nombre
      @nombre
    end
    
    def get_num_casilla_actual
      @num_casilla_actual
    end
    
    def get_salvoconducto
      @salvo_conducto
    end
    
    def get_propiedades
      @propiedades
    end
    
    def get_puede_comprar
      @puede_comprar
    end
    
    def get_saldo
      @saldo
    end
    
    def hipotecar(ip)
      resultado = false
        
        if(@encarcelado == true)
            return resultado
        end
        
        if(existe_la_propiedad(ip)==true)
            propiedad = @propiedades.at(ip)
            resultado = propiedad.hipotecar(self)
        end
        
        if(resultado == true)
            @diario.ocurre_evento("El jugador "+@nombre+ " hipoteca la propiedad "+ ip.to_s)
        end
        
        return resultado
    end
    
    def modificar_saldo(cantidad)
      @saldo = @saldo + cantidad
      @diario.ocurre_evento("Modificado el saldo del jugador " + @nombre + " saldo actual " + @saldo.to_s)
      return true
    end
    
    def mover_a_casilla(num_casilla)
      if(@encarcelado == true)
          return false
      else
          @num_casilla_actual = num_casilla
          @puede_comprar = false
          @diario.ocurre_evento("Se ha movido al jugador " + @nombre);
          return true
      end
    end
    
    def obtener_salvo_conducto(sorpresa)
      if(@encarcelado == true)
            return false
      else
            @salvo_conducto = sorpresa
            return true
      end
    end
    
    def paga(cantidad)
      return modificar_saldo(cantidad * -1)
    end
      
    def paga_alquiler(cantidad)
      if(@encarcelado == true)
          return false
      else
          return paga(cantidad)
      end
    end
    
    def paga_impuesto(cantidad)
      if(@encarcelado == true)
          return false
      else
          return paga(cantidad)
      end  
    end
      
    def pasa_por_salida
      modificar_saldo(@@PASO_POR_SALIDA)
      @diario.ocurre_evento("El jugador " + @nombre + " ha pasado por la casilla salida ")
      return true
    end
      
    def puede_comprar_casilla
      if(@encarcelado == true)
          @puede_comprar = false
          return @puede_comprar
      else
          @puede_comprar = true
          return @puede_comprar
      end
    end
      
    def recibe(cantidad)
      if(@encarcelado == true)
          return false
      else
          return modificar_saldo(cantidad)
      end
    end  
    
    def salir_carcel_pagando
      if(@encarcelado == true && puede_salir_carcel_pagando() == true)
          paga(@@PRECIO_LIBERTAD)
          @encarcelado = false
          @diario.ocurre_evento("El jugador " + @nombre + " paga 200 por salir de la carcel ")
          return true
      else
          return false
      end
    end
    
    def salir_carcel_tirando
      puede = @dado.salgo_de_la_carcel
        
        if(puede == true)
          @diario.ocurre_evento("El jugador " + @nombre + " ha tenido suerte y sale de la carcel tirando el dado ")
          @encarcelado = false
        end
        
        return puede
    end
    
    def tiene_algo_que_gestionar
      if(@propiedades.length == 0)
          return false
      else
          return true
      end
    end
    
    def tiene_salvo_conducto
      if(@salvo_conducto != nil)
         return true
      else
         return false
      end  
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
    
    private
    
    def puedo_gastar(precio)
      if(@encarcelado == true)
          return false
      else if(precio > @saldo)
              return false
           else
              return true
           end
      end
    end
    
    def puedo_edificar_casa(propiedad)
      puede = false
        
        for i in @propiedades do
            if(propiedad == i && i.get_num_casas < @@CASAS_MAX)
                puede = true
            end
        end
        
        return puede
    end
    
    def puedo_edificar_hotel(propiedad)
      puede = false
        
        for i in @propiedades do
            if(propiedad == i && i.get_num_hoteles < @@HOTELES_MAX)
                if(propiedad.get_num_casas >= get_casas_por_hoteles)
                puede = true
                end
            end
        end
        
        return puede
    end
    
    def puede_salir_carcel_pagando
      if(@saldo >= @@PRECIO_LIBERTAD)
          return true
        else
          return false
        end
    end
    
    def perder_salvo_conducto
      @salvo_conducto.usadas
      @salvo_conducto = nil
    end
    
    def get_premio_paso_salida
      @@PASO_POR_SALIDA
    end
    
    def get_precio_libertad
      @@PRECIO_LIBERTAD
    end
    
    def get_hoteles_max
      @@HOTELES_MAX
    end
    
    def get_casas_max
      @@CASAS_MAX
    end
    
    def existe_la_propiedad(ip)
      existe = false
      
      for i in @propiedades
        if(i.get_propietario == self)
          existe = true
        end
      end
      
      return existe
      
    end
   
  public
  
    def <=>(otroJugador)
      otroJugador.get_saldo <=> get_saldo
    end
    
    def paga_alquiler(cantidad)
      if(@encarcelado == true)
         return false
      else
         return paga(cantidad)
      end
    end
    
    def is_encarcelado
      if(@encarcelado == true)
        return true
      else
        return false
      end
    end
    
    def to_s
      to_s = "Nombre #{@nombre} Encarcelado #{@encarcelado} Saldo #{@saldo} CartaSalirCarcel#{@salvo_conducto} NumCasillaActual #{@num_casilla_actual} Propiedades #{@propiedades}"
    end
    
end
end
