
package com.mycompany.arqui_filtros_y_tuberias;

import java.util.List;


public class Output {
    public void ImprimirResultado(Object combinations){
         if (combinations instanceof List) {
            List<?> list = (List<?>) combinations;
            for (Object item : list) {
                System.out.println(item);
            }
        } else {
            throw new IllegalArgumentException("El input debe ser de tipo List");
        }
    }
}



