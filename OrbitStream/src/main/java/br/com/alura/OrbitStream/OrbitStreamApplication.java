package br.com.alura.OrbitStream;

import br.com.alura.OrbitStream.service.ConsumoApi;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OrbitStreamApplication implements CommandLineRunner {

	public static void main(String[] args) {

    SpringApplication.run(OrbitStreamApplication.class, args);

    }

    @Override
    public void run(String... args) throws Exception {

        var consumoApi = new ConsumoApi();

        var json = consumoApi.buscarDados("http://www.omdbapi.com/?t=Sons+of+Anarchy&apikey=32905f12");
        System.out.println(json);

        
    }
}
