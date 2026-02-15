package br.com.alura.OrbitStream.main;

import br.com.alura.OrbitStream.model.DadosSerie;
import br.com.alura.OrbitStream.model.DadosTemporada;
import br.com.alura.OrbitStream.service.ConsumoApi;
import br.com.alura.OrbitStream.service.ConverterDados;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private Scanner input = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverterDados conversor = new ConverterDados();
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=32905f12";

    public void exibeMenu() {

        System.out.println("*** MENU DA ORBIT STREAM ***");
        System.out.print("Digite o nome da série para busca: ");
        var nomeSerie = input.nextLine();
        var json = consumoApi.buscarDados( ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);

        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dados);

        List<DadosTemporada> temporadas = new ArrayList<>();

        for (int i = 1; i <= dados.totalTemporadas(); i++) {
            var jsonTemporadas = consumoApi.buscarDados(ENDERECO + nomeSerie.replace(" ", "+") + "&Season=" + i + API_KEY);

            DadosTemporada dadosTemporada = conversor.obterDados(jsonTemporadas, DadosTemporada.class);

            temporadas.add(dadosTemporada);
        }

        temporadas.forEach(System.out::println);
    }
}
