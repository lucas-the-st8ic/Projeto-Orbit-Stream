package br.com.alura.OrbitStream.main;

import br.com.alura.OrbitStream.model.DadosEpisodio;
import br.com.alura.OrbitStream.model.DadosSerie;
import br.com.alura.OrbitStream.model.DadosTemporada;
import br.com.alura.OrbitStream.service.ConsumoApi;
import br.com.alura.OrbitStream.service.ConverterDados;

import java.util.*;
import java.util.stream.Collectors;

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

        for(int i = 0; i < dados.totalTemporadas(); i++){
            List<DadosEpisodio> episodiosTemporada = temporadas.get(i).listaDeEpisodio();

            for(int j = 0; j < episodiosTemporada.size(); j++) {
                System.out.println(episodiosTemporada.get(j).titulo());
            }
        }

        temporadas.forEach(t -> t.listaDeEpisodio().forEach(e -> System.out.println(e.titulo())));
        temporadas.forEach(System.out::println);

        List<DadosEpisodio> listaEpisodios = temporadas.stream()
                .flatMap(t -> t.listaDeEpisodio().stream())
                .collect(Collectors.toList());

        System.out.println("\nTop 5 episodios: ");
        listaEpisodios.stream()
                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
                .limit(5)
                .forEach(System.out::println);
        
    }
}
