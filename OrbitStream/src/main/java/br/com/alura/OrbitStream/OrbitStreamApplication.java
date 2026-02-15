package br.com.alura.OrbitStream;

import br.com.alura.OrbitStream.main.Main;
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

        Main main = new Main();
        main.exibeMenu();

    }
}
