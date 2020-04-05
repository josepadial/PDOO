# encoding: utf-8
# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

require_relative "dado.rb"
require_relative "gestor_estados.rb"
require_relative "tablero.rb"
require_relative "jugador.rb"
require_relative "mazo_sorpresas.rb"
require_relative "estados_juego.rb"
require_relative "operaciones_juego.rb"
require_relative "titulo_propiedad.rb"
require_relative "casilla.rb"

module Civitas
  class CivitasJuego
    def initialize(nombres)
      @jugadores = Array.new
      nombres.each do |i|
        @jugadores << Jugador.new(i)
      end
      @gestor_estados = Gestor_estados.new
      @estado = @gestor_estados.estado_inicial
      @indice_jugador_actual = Dado.instance.quien_empieza(@jugadores.length)
      @mazo = MazoSorpresas.new()
      @tablero = Tablero.new(10)
      inicializar_tablero(@mazo)
      inicializar_mazo_sorpresas(@tablero)
    end
    
    private
    def avanza_jugador()
      jugador_actual = @jugadores.at(@indice_jugador_actual)
      posicion_actual = jugador_actual.num_casilla_actual
      tirada = Dado.instance.tirar
      posicion_nueva = @tablero.nueva_posicion(posicion_actual, tirada)
      casilla = @tablero.get_casilla(posicion_nueva)
      contabilizar_pasos_por_salida(jugador_actual)
      jugador_actual.mover_a_casilla(posicion_nueva)
      casilla.recibe_jugador(@indice_jugador_actual, @jugadores)
      contabilizar_pasos_por_salida(jugador_actual)
    end
    
    def contabilizar_pasos_por_salida(jugador_actual)
      while @tablero.por_salida>0
        jugador_actual.pasa_por_salida
      end
    end
    
    def pasar_turno()
      @indice_jugador_actual = (@indice_jugador_actual+1)%@jugadores.lenght
    end
    
    def inicializar_tablero(mazo)
      @tablero.añade_casilla(Casilla.ini_descanso("Salida"))
      @tablero.añade_casilla(Casilla.ini_calle(TituloPropiedad.new("C/Recogidas Nº1", 650, 70, 10, 650, 450)))
      @tablero.añade_casilla(Casilla.ini_sorpresa(mazo, "Sorpresa1"))
      @tablero.añade_casilla(Casilla.ini_calle(TituloPropiedad.new("C/Recogidas Nº2", 750, 80, 15, 700, 500)))
      @tablero.añade_casilla(Casilla.ini_calle(TituloPropiedad.new("C/Recogidas Nº3", 550, 60, -10, 600, 350)))
      @tablero.añade_casilla(Casilla.ini_descanso("Parking"))
      @tablero.añade_casilla(Casilla.ini_calle(TituloPropiedad.new("C/San Antón Nº1", 500, 50, -10, 150, 250)))
      @tablero.añade_casilla(Casilla.ini_impuesto(350, "Impuesto"))
      @tablero.añade_casilla(Casilla.ini_calle(TituloPropiedad.new("C/San Antón Nº2", 600, 70, 10, 300, 350)))
      @tablero.añade_casilla(Casilla.ini_calle(TituloPropiedad.new("C/San Antón Nº3", 550, 60, -10, 250, 300)))
      @tablero.añade_casilla(Casilla.ini_descanso("Cárcel"))
      @tablero.añade_casilla(Casilla.ini_calle(TituloPropiedad.new("Paseo del Violón Nº1", 500, 50, -20, 200, 250)))
      @tablero.añade_casilla(Casilla.ini_sorpresa(mazo, "Sorpresa2"))
      @tablero.añade_casilla(Casilla.ini_calle(TituloPropiedad.new("Paseo del Violón Nº2", 800, 70, 10, 500, 400)))
      @tablero.añade_casilla(Casilla.ini_calle(TituloPropiedad.new("Paseo del Violón Nº3", 600, 60, -15, 350, 350)))
      @tablero.añade_juez()
      @tablero.añade_casilla(Casilla.ini_sorpresa(mazo, "Sorpresa3"))
      @tablero.añade_casilla(Casilla.ini_calle(TituloPropiedad.new("Cmno de Ronda Nº1", 950, 90, 15, 900, 650)))
      @tablero.añade_casilla(Casilla.ini_calle(TituloPropiedad.new("Cmno de Ronda Nº2", 850, 80, 10, 800, 600)))
      @tablero.añade_casilla(Casilla.ini_calle(TituloPropiedad.new("Cmno de Ronda Nº3", 1000, 100, 20, 950, 750)))
    end
    
    def inicializar_mazo_sorpresas(tablero)
      @mazo.al_mazo(Sorpresa.ini_resto(TipoSorpresa::PAGARCOBRAR,1000,"Vas por la calle y te encuentras 1000€ ¡ESTAS DE SUERTE!"))
      @mazo.al_mazo(Sorpresa.ini_resto(TipoSorpresa::PAGARCOBRAR,-1000,"¡OH NO! Has perdido 1000€ Este año no podrás pagar Mordor"))
      @mazo.al_mazo(Sorpresa.ini_mov_otra(TipoSorpresa::IRCASILLA,tablero,tablero.num_casilla_carcel,"¡Ya esta bien! Ve a la cárcel directamente"))
      @mazo.al_mazo(Sorpresa.ini_mov_otra(TipoSorpresa::IRCASILLA,tablero,15,"El juez te reclama"))
      @mazo.al_mazo(Sorpresa.ini_mov_otra(TipoSorpresa::IRCASILLA,tablero,5,"Ve corriendo al parking, te esperamos"))
      @mazo.al_mazo(Sorpresa.ini_resto(TipoSorpresa::PORCASAHOTEL,500,"Cobra 500€ por cada casa o cada hotel que poseas"))
      @mazo.al_mazo(Sorpresa.ini_resto(TipoSorpresa::PORCASAHOTEL,-500,"Paga 500€ por cada casa o cada hotel que poseas"))
      @mazo.al_mazo(Sorpresa.ini_resto(TipoSorpresa::PORJUGADOR,500,"Recibes 500€ de cada jugador"))
      @mazo.al_mazo(Sorpresa.ini_resto(TipoSorpresa::PORJUGADOR,-300,"Paga 300€ a cada jugador"))
      @mazo.al_mazo(Sorpresa.ini_exit(TipoSorpresa::SALIRCARCEL,@mazo))
      @mazo.al_mazo(Sorpresa.ini_carcel(TipoSorpresa::IRCARCEL,tablero))
    end
    
    public
    
    def ranking()
      @jugadores = @jugadores.shuffle
    end
    
    def cancelar_hipoteca(ip)
      @jugadores.at(@indice_jugador_actual).cancelar_hipoteca(ip)
    end
    
    def comprar()
      jugador_actual = get_jugador_actual
      num_casilla_actual = jugador_actual.num_casilla_actual
      casilla = @tablero.get_casilla(num_casilla_actual)
      titulo = casilla.titulo_propiedad
      jugador_actual.comprar(titulo)
    end
    
    def construir_casa(ip)
      @jugadores.at(@indice_jugador_actual).construir_casa(ip)
    end
    
    def construir_hotel(ip)
      @jugadores.at(@indice_jugador_actual).construir_hotel(ip)
    end
    
    def final_del_juego()
      salida = false
      @jugadores.each do |i|
        if i.en_bancarrota
          salida = true
        end
      end
      salida
    end
    
    def get_casilla_actual
      @tablero.get_casilla(get_jugador_actual().num_casilla_actual)
    end
    
    def get_jugador_actual
      @jugadores.at(@indice_jugador_actual)
    end
    
    def hipotecar(ip)
      @jugadores.at(@indice_jugador_actual).hipotecar(ip)
    end
    
    def info_jugador_texto()
      @jugadores.at(@indice_jugador_actual).to_s
    end
    
    def salir_carcel_pagando
      @jugadores.at(@indice_jugador_actual).salir_carcel_pagando()
    end
    
    def salir_carcel_tirando
      @jugadores.at(@indice_jugador_actual).salir_carcel_tirando()
    end
    
    def siguiente_paso()
      jugador_actual = get_jugador_actual
      operacion = @gestor_estados.operaciones_permitidas(jugador_actual, @estado)
      case operacion
      when OperacionesJuego::PASARTURNO
        pasar_turno
        siguiente_paso_completado(operacion)
      when OperacionesJuego::AVANZAR
        avanza_jugador
        siguiente_paso_completado(operacion)
      end
      operacion
    end
    
    def siguiente_paso_completado(operacion)
      @estado = @gestor_estados.siguiente_estado(@jugadores.at(@indice_jugador_actual), @estado, operacion)
    end
    
    def vender(ip)
      @jugadores.at(@indice_jugador_actual).vender(ip)
    end
  end
end
