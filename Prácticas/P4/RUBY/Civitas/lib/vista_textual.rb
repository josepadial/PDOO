#encoding:utf-8
require_relative 'operaciones_juego'
require 'io/console'
require_relative"operacion_inmobiliaria.rb"
require_relative"respuestas.rb"
require_relative"salidas_carcel.rb"
require_relative"operaciones_juego.rb"
require_relative"civitas_juego.rb"

module Civitas

  class Vista_textual
    
    def initialize
      @juego_model
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
        puts tab+index.to_s+"-"+l.to_s
        index += 1
      }

      opcion = lee_entero(lista.length,
                          "\n"+tab+"Elige una opcion: ",
                          tab+"Valor erroneo")
      return opcion
    end
  
    def salir_carcel()
      lista_opciones = [Salidas_carcel::PAGANDO,Salidas_carcel::TIRANDO]
      opcion = menu("Elige la forma para intentar salir de la carcel",lista_opciones)
      return lista_opciones[opcion]
    end

    
    def comprar
      lista_respuestas = [Respuestas::NO,Respuestas::SI]
      
      opcion = menu("¿Quieres comprar?",lista_respuestas)
      
      return lista_respuestas[opcion]
    end

    def gestionar
      
      lista_operaciones_inmobiliarias = [Operaciones_inmobiliarias::VENDER,Operaciones_inmobiliarias::HIPOTECAR,Operaciones_inmobiliarias::CANCELAR_HIPOTECA,Operaciones_inmobiliarias::CONSTRUIR_CASA,Operaciones_inmobiliarias::CONSTRUIR_HOTEL,Operaciones_inmobiliarias::TERMINAR]
      opcion = menu("¿Que gestion inmobiliaria quieres realizar?", lista_operaciones_inmobiliarias)
      @i_gestion = opcion
      
      puts "IGESTION 0 " + @i_gestion.to_s
      
      if(@i_gestion != 5)
        propiedadesASString = @juego_model.get_jugador_actual.cadena_propiedades
        propiedad = menu("¿Sobre que propiedad quieres realizar la operacion?",propiedadesASString)
        @i_propiedad = propiedad
      end
      
    end

    def get_gestion
      return @i_gestion
    end

    def get_propiedad
      return @i_propiedad
    end

    def mostrar_siguiente_operacion(operacion)
      puts "Siguiente operacion #{operacion}"
    end

    def mostrar_eventos
      while(@juego_model.get_diario.eventos_pendientes == true)
        puts @juego_model.get_diario.leer_evento
      end
    end

    def set_civitas_juego(civitas)
         @juego_model=civitas
         self.actualizar_vista
    end

    def actualizar_vista
      puts "Jugador actual \n #{@juego_model.get_jugador_actual} \n Casilla actual #{@juego_model.get_casilla_actual}"
    end

    
  end

end
