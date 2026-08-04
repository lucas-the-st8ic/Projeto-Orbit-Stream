package br.com.alura.OrbitStream.controller;

import br.com.alura.OrbitStream.model.Serie;
import br.com.alura.OrbitStream.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SerieController {

    @Autowired
    private SerieRepository serieRepository;

    @GetMapping("/series")
    public List<Serie> obterSeries() {
        return serieRepository.findAll();
    }
}
