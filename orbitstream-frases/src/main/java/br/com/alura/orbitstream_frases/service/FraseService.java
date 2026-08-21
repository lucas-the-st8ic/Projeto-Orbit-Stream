package br.com.alura.orbitstream_frases.service;

import br.com.alura.orbitstream_frases.dto.FraseDTO;
import br.com.alura.orbitstream_frases.model.Frase;
import br.com.alura.orbitstream_frases.repository.FraseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FraseService {

    @Autowired
    FraseRepository fraseRepository;

    public FraseDTO obterFraseAleatoria() {
        Frase frase = fraseRepository.buscarFrasealeatoria();
        return new FraseDTO(frase.getTitulo(), frase.getFrase(), frase.getPersonagem(), frase.getPoster());
    }
}
