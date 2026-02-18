package br.com.alura.OrbitStream.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Arrays;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosTemporada( @JsonAlias("Season") Integer numeroTemporada,
                              @JsonAlias("Episodes") List<DadosEpisodio> listaDeEpisodio) {

    @Override
    public String toString() {
        return "Temporada: " + numeroTemporada +
                "Episódios: " + listaDeEpisodio;
    }

}
