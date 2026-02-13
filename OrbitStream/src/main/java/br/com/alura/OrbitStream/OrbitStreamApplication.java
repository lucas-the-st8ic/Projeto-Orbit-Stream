package br.com.alura.OrbitStream;

import br.com.alura.OrbitStream.model.DadosEpisodio;
import br.com.alura.OrbitStream.model.DadosSerie;
import br.com.alura.OrbitStream.model.DadosTemporada;
import br.com.alura.OrbitStream.service.ConsumoApi;
import br.com.alura.OrbitStream.service.ConverterDados;
import br.com.alura.OrbitStream.service.IConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class OrbitStreamApplication implements CommandLineRunner {

	public static void main(String[] args) {
    /*
     Endereços para consulta e testes:
        https://www.omdbapi.com/?t=Sons+of+Anarchy&apikey=32905f12

        https://www.omdbapi.com/?t=Sons+of+Anarchy&Season=1&Episode=1&apikey=32905f12
    */
    SpringApplication.run(OrbitStreamApplication.class, args);

    }

    @Override
    public void run(String... args) throws Exception {

        var consumoApi = new ConsumoApi();
        ConverterDados conversor = new ConverterDados();

        //DADOS DA SÉRIE
        var json = consumoApi.buscarDados("https://www.omdbapi.com/?t=Sons+of+Anarchy&apikey=32905f12");
        System.out.println(json);

        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);

        System.out.println(dados);
        // FIM DADOS DA SÉRIE


        //DADOS DE EPISÓDIOS
       /*
       var json = consumoApi.buscarDados("https://www.omdbapi.com/?t=Sons+of+Anarchy&Season=1&Episode=2&apikey=32905f12");

        System.out.println(json);

        DadosEpisodio dadosEpisodio = conversor.obterDados(json, DadosEpisodio.class);

        System.out.println(dadosEpisodio);


        System.out.println(json);*/
        // FIM DADOS DE EPISÓDIOS

        List <DadosTemporada> temporadas = new ArrayList<>();

        for (int i = 1; i <= dados.totalTemporadas(); i++) {
            var jsonTemporadas = consumoApi.buscarDados("https://www.omdbapi.com/?t=Sons+of+Anarchy&Season=" + i + "&apikey=32905f12");

            DadosTemporada dadosTemporada = conversor.obterDados(jsonTemporadas, DadosTemporada.class);

            temporadas.add(dadosTemporada);
        }

        temporadas.forEach(System.out::println);
    }
}
