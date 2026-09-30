
package com.mycompany.arqui_filtros_y_tuberias;


public class Tuberia {
    Input input = new Input();
    Filtro tokenizer = new Tokenizer();
    Filtro combinaciones = new Combinations();
    Filtro sorting = new Sorting();
    Output output = new Output();
    
    public void ejecutarTuberia(){
        output.ImprimirResultado(sorting.ejecutar(combinaciones.ejecutar(tokenizer.ejecutar(input.ObtenerDatosDeEntrada()))));
    }
}
