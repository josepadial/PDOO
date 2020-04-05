# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

require_relative"operaciones_inmobiliarias.rb"

module Civitas
class Operacion_inmobiliaria
  
  def initialize(index,operac)
    @indice_propiedad = index
    @operacion = operac
  end
  
  def get_indice_propiedad
    return @indice_propiedad
  end
    
  def get_operacion
    return @operacion
  end
    
end
end
