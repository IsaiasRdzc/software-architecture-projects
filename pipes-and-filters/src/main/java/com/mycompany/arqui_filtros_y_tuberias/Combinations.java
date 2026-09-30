
package com.mycompany.arqui_filtros_y_tuberias;

import java.util.ArrayList;
import java.util.List;


public class Combinations extends Filtro{

    @Override
    public Object ejecutar(Object datos) {
       if (datos instanceof List) {
            List<String> words = (List<String>) datos;
            List<String> combinations = new ArrayList<>();
            generateKWICCombinations(words, combinations);
            return combinations;
        } else {
            throw new IllegalArgumentException("El input debe ser de tipo List<String>");
        }
    }
    
    private void generateKWICCombinations(List<String> words, List<String> combinations) {
    for (int i = 0; i < words.size(); i++) {
        StringBuilder combination = new StringBuilder();
        // Agregar palabras desde el índice i hasta el final
        for (int j = i; j < words.size(); j++) {
            combination.append(words.get(j)).append(" ");
        }
        // Agregar palabras desde el inicio hasta el índice i
        for (int j = 0; j < i; j++) {
            combination.append(words.get(j)).append(" ");
        }
        // Agregar la combinación a la lista
        combinations.add(combination.toString().trim());
    }
}
}




