package br.com.alura.OrbitStream.repository;

import br.com.alura.OrbitStream.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SerieRepository extends
        JpaRepository<Serie, Long> {
}
