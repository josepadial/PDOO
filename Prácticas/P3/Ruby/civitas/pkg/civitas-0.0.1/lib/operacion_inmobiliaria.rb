# encoding: utf-8
# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

module Civitas
  class OperacionInmobiliaria
     attr_reader :num_propiedades, :gestion
    def initialize(gest,ip)
      @num_propiedades = ip
      @gestion = gest
    end
  end
end