package br.com.alura.OrbitStream.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosSerie(@JsonAlias("Title") String titulo,
                         @JsonAlias("totalSeasons") Integer totalTemporadas,
                         @JsonAlias("imdbRating") String avaliacao,
                         @JsonAlias("Genre") String genero,
                         @JsonAlias("Actors") String atores,
                         @JsonAlias("Poster") String poster,
                         @JsonAlias("Plot") String sinopse) {

    @Override
    public String toString() {
        return "\nTitulo: " +titulo+
                "\nTotal de Temporadas: " +totalTemporadas+
                "\nAvaliações: " +avaliacao+
                "\nGenero: " +genero+
                "\nAtores: " +atores+
                "\nPoster: " +poster+
                "\nSinopse: " +sinopse;
    }
}
