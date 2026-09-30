
package com.mycompany.arqui_filtros_y_tuberias;

import java.util.Arrays;


public class Tokenizer extends Filtro{

    @Override
    public Object ejecutar(Object datos) {
       String[] tokens = ((String)datos).trim().split("\\s+");
        return Arrays.asList(tokens);
    }
    
}
