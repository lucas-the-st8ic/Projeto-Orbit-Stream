package br.com.alura.orbitstream_frases.controller;


import br.com.alura.orbitstream_frases.dto.FraseDTO;
import br.com.alura.orbitstream_frases.service.FraseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/series")
public class FraseController {

    @Autowired
    private FraseService fraseService;

    @GetMapping("/frases")
    public FraseDTO obterFraseAleatoria() {
        return fraseService.obterFraseAleatoria();
    }
}
