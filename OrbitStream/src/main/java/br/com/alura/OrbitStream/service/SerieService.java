package br.com.alura.OrbitStream.service;

import br.com.alura.OrbitStream.dto.SerieDTO;
import br.com.alura.OrbitStream.model.Serie;
import br.com.alura.OrbitStream.repository.SerieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SerieService {

    private final SerieRepository serieRepository;

    public SerieService(SerieRepository serieRepository) {
        this.serieRepository = serieRepository;
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
