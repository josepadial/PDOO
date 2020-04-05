# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

require_relative"mazo_sorpresas.rb"
require_relative"tablero.rb"
require_relative"operaciones_juego.rb"
require_relative"dado.rb"
require_relative"jugador.rb"
require_relative"estados_juego.rb"
require_relative"gestor_estados.rb"
require_relative"diario.rb"
require_relative"casilla.rb"
require_relative"casilla_calle.rb"
require_relative"casilla_impuesto.rb"
require_relative"casilla_juez.rb"
require_relative"casilla_sorpresa.rb"
require_relative"controlador.rb"
require_relative"operacion_inmobiliaria.rb"
require_relative"operaciones_inmobiliarias.rb"
require_relative"respuestas.rb"
require_relative"sorpresa_convertirse.rb"
require_relative"sorpresa_ir_carcel.rb"
require_relative"sorpresa_ir_casilla.rb"
require_relative"sorpresa_pagar_cobrar.rb"
require_relative"sorpresa_por_casa_hotel.rb"
require_relative"sorpresa_por_jugador.rb"
require_relative"sorpresa_salida_carcel.rb"
require_relative"salidas_carcel.rb"
require_relative"titulo_propiedad.rb"
require_relative"vista_textual.rb"

module Civitas
class Civitas_juego
  
  public
  def initialize(nombres)
    @indice_jugador_actual 
    @dado = Dado.instance
    @operacion
    @mazo = Mazo_sorpresas.new(false)
    @tablero = Tablero.new(8)
    @jugadores = Array.new
    @gestor_estados = Gestor_estados.new
    @estado = @gestor_estados.estado_inicial
    @diario = Diario.instance
   
    for i in nombres
      @jugadores << Jugador.new(i)
    end
    
    @indice_jugador_actual = @dado.tirar % @jugadores.length
        
    inicializar_tablero(@mazo);
    inicializar_mazo_sorpresa(@tablero);
  end
  
  def cancelar_hipoteca(ip)
    return @jugadores.at(@indice_jugador_actual).cancelar_hipoteca(ip)
  end
  
  def comprar
     numCas = @jugadores.at(@indice_jugador_actual).get_num_casilla_actual
     propiedad = @tablero.get_casilla(numCas).titulo
                
     return @jugadores.at(@indice_jugador_actual).comprar(propiedad)
  end
  
  def construir_casa(ip)
    @jugadores.at(@indice_jugador_actual).construir_casa(ip)
  end
  
  def construir_hotel(ip)
    @jugadores.at(@indice_jugador_actual).construir_hotel(ip)
    puts "HE ENTRADO"
  end
  
  def final_del_juego
    final = false
    tamanio = @jugadores.length
    i = 0
    
    while(i<tamanio) 
      if(@jugadores.at(i).en_bancarrota == true)
        final = true
      end
      i = i+1
    end
    
    return final
    
  end
  
  def get_casilla_actual
    numCas = @jugadores.at(@indice_jugador_actual).get_num_casilla_actual
    return @tablero.get_casilla(numCas)
  end
  
  def get_diario
    return @diario
  end
  
  def get_jugador_actual
    return @jugadores.at(@indice_jugador_actual)
  end
  
  def hipotecar(ip)
    return @jugadores.at(@indice_jugador_actual).hipotecar(ip)
  end
  
  def info_jugador_texto
    return @jugadores.at(@indice_jugador_actual).to_s
  end
  
  def salir_carcel_pagando
    return @jugadores.at(@indice_jugador_actual).salir_carcel_pagando
  end
  
  def salir_carcel_tirando
    return @jugadores.at(@indice_jugador_actual).salir_carcel_tirando
  end
  
  def siguiente_paso_completado(operacion)
    @estado = @gestor_estados.siguiente_estado(@jugadores.at(@indice_jugador_actual), @estado, operacion);
  end
  
  def vender(ip)
    @jugadores.at(@indice_jugador_actual).vender(ip)
  end
  
  def ranking
    ranking = Array.new
    ranking = @jugadores
    ranking.sort
    return ranking
  end
  
  def avanza_jugador
    jugadorActual = @jugadores.at(@indice_jugador_actual)
    posAct = jugadorActual.get_num_casilla_actual
    tirada = @dado.tirar
    posNew = @tablero.nueva_posicion(posAct,tirada)
    casillaActual = @tablero.get_casilla(posNew)
    contabilizar_pasos_por_salida(jugadorActual)
    jugadorActual.mover_a_casilla(posNew)
    casillaActual.recibe_jugador(@indice_jugador_actual, @jugadores)
    contabilizar_pasos_por_salida(jugadorActual)
  end
  
  def contabilizar_pasos_por_salida(jugador_actual)
    if(@tablero.get_por_salida > 0)
        jugador_actual.pasa_por_salida
    end
  end
  
  def inicializar_mazo_sorpresa(tablero)
    @mazo.al_mazo(SorpresaIrCarcel.new(tablero,"Vas a la carcel"))
    @mazo.al_mazo(SorpresaIrCasilla.new(tablero,10,"Desplazate hacia la casilla indicada"))
    @mazo.al_mazo(SorpresaIrCasilla.new(tablero,16,"Desplazate hacia la casilla indicada"))
    @mazo.al_mazo(SorpresaIrCasilla.new(tablero,tablero.get_carcel,"A la carcel"))
    @mazo.al_mazo(SorpresaPagarCobrar.new(tablero,1000,"Recibes la cantidad indicada"))
    @mazo.al_mazo(SorpresaPagarCobrar.new(tablero,-1000,"Pierdes la cantidad indicada"))
    @mazo.al_mazo(SorpresaPorCasaHotel.new(tablero,-50,"Debes pagar los impuestos de tus propiedades"))
    @mazo.al_mazo(SorpresaPorCasaHotel.new(tablero,50,"Hacienda te ha hecho un regalito"))
    @mazo.al_mazo(SorpresaPorJugador.new(tablero,-100,"El jugador debe pagar la cantidad indicada a cada jugador"))
    @mazo.al_mazo(SorpresaPorJugador.new(tablero,100,"El jugador recibe la cantidad indicada de cada jugador"))
    @mazo.al_mazo(SorpresaSalidaCarcel.new(@mazo))
  end
  
  def inicializar_tablero(mazo)
    @tablero.aniade_casilla(CasillaCalle.new(Titulo_propiedad.new("Iglesia", 100, 19, 300, 150, 300)))
    @tablero.aniade_casilla(CasillaCalle.new(Titulo_propiedad.new("C/ Istan", 75, 19, 100, 100, 500)))
    @tablero.aniade_casilla(CasillaCalle.new(Titulo_propiedad.new("C/ Don Vito", 125, 19, 200, 200, 350)))
    @tablero.aniade_casilla(Casilla_Sorpresa.new("SORPRESA",mazo))
    @tablero.aniade_casilla(CasillaCalle.new(Titulo_propiedad.new("C/ Pepe Osorio", 50, 19, 100, 300, 350)))
    @tablero.aniade_casilla(CasillaCalle.new(Titulo_propiedad.new("C/ Pajaritos", 150, 19, 300, 500, 300)))
    @tablero.aniade_juez()
    @tablero.aniade_casilla(Casilla_Sorpresa.new("SORPRESA",mazo))
    @tablero.aniade_casilla(CasillaCalle.new(Titulo_propiedad.new("C/ Lagasca", 200, 19, 175, 120, 450)))
    @tablero.aniade_casilla(CasillaCalle.new(Titulo_propiedad.new("C/ San Miguel", 75, 19, 225, 150, 225)))
    @tablero.aniade_casilla(CasillaImpuesto.new("IMPUESTO",75))
    @tablero.aniade_casilla(CasillaCalle.new(Titulo_propiedad.new("C/ Pizarro", 215, 19, 420, 350, 200)))
    @tablero.aniade_casilla(Casilla_Sorpresa.new("SORPRESA",mazo))
    @tablero.aniade_casilla(CasillaCalle.new(Titulo_propiedad.new("C/ Oriental", 140, 19, 330, 500, 380)))
    @tablero.aniade_casilla(Casilla.new("PARKING"))
    @tablero.aniade_casilla(CasillaCalle.new(Titulo_propiedad.new("C/ Dolores", 200, 19, 400, 550, 325)))
    @tablero.aniade_casilla(CasillaCalle.new(Titulo_propiedad.new("C/ Caballero", 300, 19, 400, 700, 500)))
    @tablero.aniade_casilla(CasillaCalle.new(Titulo_propiedad.new("C/ Ruiz", 300, 19, 500, 800, 550)))
  end
  
  def pasar_turno
    @indice_jugador_actual = (@indice_jugador_actual + 1) % @jugadores.length
  end
  
  def siguiente_paso
    jugadorActual = @jugadores.at(@indice_jugador_actual)
    operacion = @gestor_estados.operaciones_permitidas(jugadorActual,@estado)
    puts "OPERACION PERMITIDA Y ESTADO " + operacion.to_s + " " + @estado.to_s
        
    if(operacion == Operaciones_juego::PASAR_TURNO)
            pasar_turno
            siguiente_paso_completado(operacion)
    end
         
    if(operacion == Operaciones_juego::AVANZAR)
            avanza_jugador
            siguiente_paso_completado(operacion)
    end
    
        return operacion
  end
  
end
end
