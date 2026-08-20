package br.com.alura.orbitstream_frases.controller;


import br.com.alura.orbitstream_frases.dto.FraseDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/series")
public class FraseController {

    @GetMapping("/frases")
    public FraseDTO obterFraseAleatoria() {

    }
}
