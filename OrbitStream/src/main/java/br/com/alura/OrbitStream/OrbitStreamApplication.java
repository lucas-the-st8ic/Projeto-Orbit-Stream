package br.com.alura.OrbitStream;

import br.com.alura.OrbitStream.model.DadosEpisodio;
import br.com.alura.OrbitStream.model.DadosSerie;
import br.com.alura.OrbitStream.service.ConsumoApi;
import br.com.alura.OrbitStream.service.ConverterDados;
import br.com.alura.OrbitStream.service.IConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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

        /*var json = consumoApi.buscarDados("https://www.omdbapi.com/?t=Sons+of+Anarchy&apikey=32905f12");
        System.out.println(json);*/

        ConverterDados conversor = new ConverterDados();
        
/*        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);

        System.out.println(dados);*/

        var json = consumoApi.buscarDados("https://www.omdbapi.com/?t=Sons+of+" +
                "Anarchy&Season=1&Episode=1&apikey=32905f12");
        System.out.println(json);
        DadosEpisodio dadosEpisodio = conversor.obterDados(json, DadosEpisodio.class);


        System.out.println(dadosEpisodio);

    }
}
