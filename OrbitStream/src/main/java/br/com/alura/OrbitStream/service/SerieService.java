package br.com.alura.OrbitStream.service;

import br.com.alura.OrbitStream.dto.EpisodioDTO;
import br.com.alura.OrbitStream.dto.SerieDTO;
import br.com.alura.OrbitStream.model.Categoria;
import br.com.alura.OrbitStream.model.Serie;
import br.com.alura.OrbitStream.repository.SerieRepository;
import org.springframework.data.repository.core.support.RepositoryMethodInvocationListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SerieService {

    private final SerieRepository serieRepository;
    private final RepositoryMethodInvocationListener repositoryMethodInvocationListener;

    public SerieService(SerieRepository serieRepository, RepositoryMethodInvocationListener repositoryMethodInvocationListener) {
        this.serieRepository = serieRepository;
        this.repositoryMethodInvocationListener = repositoryMethodInvocationListener;
    }

    public List<SerieDTO> obterSeries() {
        return converteDados(serieRepository.findAll());
    }

    public List<SerieDTO> obterTop5Series() {
        return converteDados(serieRepository
                .findTop5ByOrderByAvaliacaoDesc());
    }

    public List<SerieDTO> obterLancamentos() {
        return converteDados(serieRepository.lancamentosMaisRecentes());
    }

    private List<SerieDTO> converteDados(List<Serie> series) {
        return series.stream()
                .map(s -> new SerieDTO(s.getId(),s.getTitulo(),
                        s.getTotalTemporadas(),s.getAvaliacao(),
                        s.getGenero(),s.getAtores(),s.getPoster(),
                        s.getSinopse())).collect(Collectors.toList());
    }

    public SerieDTO obterSeriePorId(Long id) {
        Optional<Serie> serie = serieRepository.findById(id);
        if (serie.isPresent()) {
            Serie s = serie.get();
            return new SerieDTO(s.getId(),s.getTitulo(),
                    s.getTotalTemporadas(),s.getAvaliacao(),
                    s.getGenero(),s.getAtores(),s.getPoster(),
                    s.getSinopse());
        } else  {
            return null;
        }
    }
    public List<EpisodioDTO> obterTodasTemporadas(Long id) {
        Optional<Serie> serie = serieRepository.findById(id);
        if (serie.isPresent()) {
            Serie s = serie.get();
            return s.getEpisodios()
                    .stream()
                    .map(e -> new EpisodioDTO(e.getTemporada(),
                            e.getTitulo(), e.getNumeroEpisodio()))
                    .collect(Collectors.toList());
        } else  {
            return null;
        }
    }

    public List<EpisodioDTO> obterTemporadasPorNumero(Long id, Long numero) {
        return serieRepository
                .obterEpisodiosPorTemporada(id, numero)
                .stream()
                .map(e -> new EpisodioDTO(e.getTemporada(),
                        e.getTitulo(), e.getNumeroEpisodio()))
                .collect(Collectors.toList());
    }

    public List<SerieDTO> obterSeriesPorCategoria(String nomeGenero) {

        Categoria categoria = Categoria.fromPortugues(nomeGenero);
        return converteDados(serieRepository.findByGenero(categoria));
    }

    public List<EpisodioDTO> obterTop5Episodios(Long id) {
        return serieRepository
                .obterTop5EpisodiosDaSerie(id)
                .stream().map(e -> new EpisodioDTO(e.getTemporada(),
                e.getTitulo(), e.getNumeroEpisodio())).collect(Collectors.toList());
    }

    //======================================

    public void traduzirSinopsesExistentes() {

        List<Serie> series = serieRepository.findAll();
        for (Serie serie : series) {
            try {
                String sinopseTraduzida =
                        ConsultaGeminiAI.traduzirSinopse(
                                serie.getSinopse()
                        );
                serie.setSinopse(sinopseTraduzida);
                serieRepository.save(serie);
            } catch (Exception e) {
                System.out.println(
                        "Erro ao traduzir: "
                                + serie.getTitulo()
                );
            }
        }
    }
}
