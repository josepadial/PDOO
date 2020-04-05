#encoding:utf-8
require_relative 'operaciones_juego.rb'
require_relative 'civitas_juego.rb'
require_relative 'salidas_carcel.rb'
require_relative 'respuestas.rb'
require_relative 'diario.rb'
require_relative 'casilla.rb'
require_relative 'operaciones_inmobiliarias.rb'
require_relative 'jugador.rb'
require_relative 'titulo_propiedad.rb'
require 'io/console'

module JuegoTexto

  class Vista_textual
    include Civitas
    attr_reader :i_gestion, :i_propiedad, :juego_model
    
    def initialize
      @i_gestion = -1
      @i_propiedad = -1
    end
    
    def mostrar_estado(estado)
      puts estado
    end

    
    def pausa
      print "Pulsa una tecla"
      STDIN.getch
      print "\n"
    end

    def lee_entero(max,msg1,msg2)
      ok = false
      begin
        print msg1
        cadena = gets.chomp
        begin
          if (cadena =~ /\A\d+\Z/)
            numero = cadena.to_i
            ok = true
          else
            raise IOError
          end
        rescue IOError
          puts msg2
        end
        if (ok)
          if (numero >= max)
            ok = false
          end
        end
      end while (!ok)

      return numero
    end



    def menu(titulo,lista)
      tab = "  "
      puts titulo
      index = 0
      lista.each { |l|
        puts tab+index.to_s+"-"+l
        index += 1
      }

      opcion = lee_entero(lista.length,
                          "\n"+tab+"Elige una opción: ",
                          tab+"Valor erróneo")
      return opcion
    end
    
    def salir_carcel
      lista_respuestas = [SalidasCarcel::PAGANDO,SalidasCarcel::TIRANDO]
      opcion = menu("Elige la forma para intentar salir de la carcel",["PAGANDO","TIRANDO"])
      lista_respuestas[opcion]
    end
    
    def comprar
      lista_respuestas = [Respuestas::NO,Respuestas::SI]
      opcion = menu(@juego_model.get_casilla_actual.to_s + "\n¿Desea comprar la calle?",["NO","SI"])
      lista_respuestas[opcion]
    end

    def gestionar
      lista_respuestas = [OperacionesInmobiliarias::VENDER,OperacionesInmobiliarias::HIPOTECAR,OperacionesInmobiliarias::CANCERLARHIPOTECA,OperacionesInmobiliarias::CONSTRUIRCASA,OperacionesInmobiliarias,OperacionesInmobiliarias::CONSTRUIRHOTEL,OperacionesInmobiliarias::TERMINAR]
      opcion = menu("Eliga la gestion que desea hacer",["Vender", "Hipotecar", "Cancelar Hipoteca", "Construir Casa", "Construir Hotel", "Terminar"])
      @i_gestion = lista_respuestas[opcion]
      
      nombres = Array.new
      
      for i in 0..@juego_model.get_jugador_actual.propiedades.length
        nombres << @juego_model.get_jugador_actual.propiedades.at(i).nombre
      end
      @i_propiedad = menu("¿Sobre que casilla deseas operar?",nombres)
    end

    def mostrarSiguienteOperacion(operacion)
      case operacion
      when OperacionesJuego::AVANZAR
        puts "La siguiente operacion es avanzar"
      when OperacionesJuego::COMPRAR
        puts "La siguiente operacion es comprar"
      when OperacionesJuego::GESTIONAR
        puts "La siguiente operacion es gestionar"
      when OperacionesJuego::PASARTURNO
        puts "La siguiente operacion es pasar turno"
      else
        puts "La siguiente operacion es salir de la carcel"
      end
    end

    def mostrarEventos
      while Diario.instance.eventos_pendientes
        puts Diario.instance.leer_evento
      end
    end

    def setCivitasJuego(civitas)
         @juego_model = civitas
    end

    def actualizarVista
      puts @juego_model.info_jugador_texto
    end

    
  end

end
