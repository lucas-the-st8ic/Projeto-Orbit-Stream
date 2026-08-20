package br.com.alura.orbitstream_frases.repository;

import br.com.alura.orbitstream_frases.model.Frase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FraseRepository extends JpaRepository<Frase, Long> {
}
