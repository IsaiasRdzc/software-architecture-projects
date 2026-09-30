package com.mycompany.arqui_filtros_y_tuberias;

import java.util.Collections;
import java.util.List;


public class Sorting extends Filtro{

    @Override
    public Object ejecutar(Object datos) {
      if (datos instanceof List) {
            List<String> combinationList = (List<String>) datos;
            Collections.sort(combinationList);
            return combinationList;
        } else {
            throw new IllegalArgumentException("El datos debe ser de tipo List<String> para Ordenar");
        }
    }
    
}


