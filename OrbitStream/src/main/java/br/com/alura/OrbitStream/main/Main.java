package br.com.alura.OrbitStream.main;

import br.com.alura.OrbitStream.service.ConsumoApi;
import br.com.alura.OrbitStream.service.ConverterDados;

import java.util.Scanner;

public class Main {

    private Scanner input = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();

    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=32905f12";

    public void exibeMenu() {

        System.out.println("*** MENU DA ORBIT STREAM ***");
        System.out.print("Digite o nome da série para busca: ");
        var nomeSerie = input.nextLine();

        var json = consumoApi.buscarDados( ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        
        //"https://www.omdbapi.com/?t=Sons+of+Anarchy&apikey=32905f12"
    }
}
