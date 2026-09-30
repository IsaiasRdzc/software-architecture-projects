package com.mycompany.arqui_filtros_y_tuberias;

import java.util.Scanner;


public class Input {
    public String ObtenerDatosDeEntrada(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese una frase: ");
        String input = scanner.nextLine();
        return input;
    }
}



