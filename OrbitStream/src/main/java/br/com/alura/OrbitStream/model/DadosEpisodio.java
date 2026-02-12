package br.com.alura.OrbitStream.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosEpisodio(@JsonAlias("Title") String titulo,
                            @JsonAlias("Season") Integer numeroTemporada,
                            @JsonAlias("Episode") Integer numeroEpisodio,
                            @JsonAlias("imdbRating") String avaliacao,
                            @JsonAlias("Released") String dataLancamento) {


    @Override
    public String toString() {
        return  "\nTitulo: " +titulo+
                "\nTemporada: " +numeroTemporada+
                "\nEpisódio: " +numeroEpisodio+
                "\nData de lançamento: " +dataLancamento+
                "\nAvaliações: " +avaliacao;
    }
}
