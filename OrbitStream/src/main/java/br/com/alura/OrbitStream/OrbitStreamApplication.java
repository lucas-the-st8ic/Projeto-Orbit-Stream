package br.com.alura.OrbitStream;

import br.com.alura.OrbitStream.main.Main;
import br.com.alura.OrbitStream.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OrbitStreamApplication implements CommandLineRunner {

    @Autowired
    private SerieRepository repositorio;

    public static void main(String[] args) {
        SpringApplication.run(OrbitStreamApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        // ==== Descomente a linha abaixo para rodar o menu no terminal ====
         Main main = new Main(repositorio);
         /*main.exibeMenu();*/

        // O servidor web (Tomcat + endpoints REST) SEMPRE sobe automaticamente,
        // independente da linha acima estar comentada ou não.
    }
}
