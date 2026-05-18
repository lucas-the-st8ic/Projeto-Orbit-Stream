package br.com.alura.OrbitStream.main;

import br.com.alura.OrbitStream.model.*;
import br.com.alura.OrbitStream.repository.SerieRepository;
import br.com.alura.OrbitStream.service.ConsumoApi;
import br.com.alura.OrbitStream.service.ConverterDados;

import java.util.*;
import java.util.stream.Collectors;

public class Main {

    private Scanner input = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverterDados conversor = new ConverterDados();
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=32905f12";
    private List<DadosSerie> dadosSeries = new ArrayList<>();
    private List<Serie> series = new ArrayList<>();
    private SerieRepository repositorio;


    public Main(SerieRepository repositorio) {
        this.repositorio = repositorio;
    }


    public void exibeMenu() {

        var opcao = Integer.MIN_VALUE;

        while(opcao != 0) {
            var menu = """
        =========================
        1 - Buscar séries
        2 - Buscar episódios
        3 - Consultar lista de séries
        4 - Consultar série por título
        5 - Buscar séries por ator
        6 - Top 5 séries
        7 - Buscar séries por categoria
        8 - Buscar séries por número de temporadas e 
        avaliação
        0 - Sair
        -------------------------
        Digite sua opção:  """;

            System.out.print(menu);
            opcao = input.nextInt();
            input.nextLine();

            switch (opcao) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    listarSeriesBuscadas();
                    break;
                case 4:
                    buscarSeriePorTitulo();
                    break;
                case 5:
                    buscarSeriesPorAtor();
                    break;
                case 6:
                    buscarTop5Series();
                    break;
                case 7:
                    buscarSeriesPorCategoria();
                    break;
                case 8:
                    buscarSeriesPorNumeroDeTemporadasEAvaliacao();
                    break;
                case 9:
                    buscarEpisodioPorTrecho();
                    break;
                case 0:
                    System.out.println("Encerrando...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }


    private void buscarSerieWeb() {
        DadosSerie dados = getDadosSerie();
        Serie serie = new Serie(dados);
        //dadosSeries.add(dados);
        repositorio.save(serie);
        System.out.println(dados);
    }

    private DadosSerie getDadosSerie() {
        System.out.print("Digite o nome da série para busca: ");
        var nomeSerie = input.nextLine();
        var json = consumoApi.buscarDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        return dados;
    }

    private void buscarEpisodioPorSerie(){

        listarSeriesBuscadas();

        System.out.print("Escolha uma série pelo nome: ");
        var nomeSerie = input.nextLine();

        Optional<Serie> serie = repositorio.findByTituloContainingIgnoreCase(nomeSerie);

        if (serie.isPresent()) {

            var serieEncontrada = serie.get();

            List<DadosTemporada> temporadas = new ArrayList<>();

            for (int i = 1; i <= serieEncontrada.getTotalTemporadas(); i++) {
                var json = consumoApi.buscarDados(ENDERECO + serieEncontrada.getTitulo().replace(" ", "+") + "&season=" + i + API_KEY);
                DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
                temporadas.add(dadosTemporada);
            }
            temporadas.forEach(System.out::println);

            List<Episodio> episodios = temporadas.stream()
                    .flatMap(d -> d.listaDeEpisodio()
                            .stream()
                            .map(e -> new Episodio(d.numeroTemporada(), e)))
                    .collect(Collectors.toList());

                serieEncontrada.setEpisodios(episodios);
                repositorio.save(serieEncontrada);

        } else {
            System.out.println("Série não encontrada!");
        }
    }

    public void setDadosSeries(List<DadosSerie> dadosSeries) {
        this.dadosSeries = dadosSeries;
    }

    private void listarSeriesBuscadas() {
        series = repositorio.findAll();
        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
        
    }

    private void buscarSeriePorTitulo() {
        System.out.print("Escolha uma série pelo nome: ");
        var nomeSerie = input.nextLine();

        Optional<Serie> serieBuscada = repositorio.findByTituloContainingIgnoreCase(nomeSerie);
        if (serieBuscada.isPresent()) {
            System.out.println("========Dados da série======");
            System.out.println(serieBuscada.get());
        } else {
            System.out.println("Série não encontrada!!");
        }
    }

    private void buscarSeriesPorAtor() {
        System.out.print("Digite o nome de um ator: ");
        var nomeAtor = input.nextLine();

        System.out.print("Digite uma avaliação minima para busca: ");
        var avaliacaoMinima = input.nextDouble();

        List<Serie> seriesEncontradas = repositorio
                .findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(nomeAtor, avaliacaoMinima);

        System.out.println("===Séries com este ator===");
        seriesEncontradas.forEach(s -> System.out.println(s.getTitulo()
        + "\nAvaliação: " + s.getAvaliacao()));
    }

    private void buscarTop5Series() {
        List<Serie> serieTop = repositorio.findTop5ByOrderByAvaliacaoDesc();

        System.out.println("===Top 5 séries===");
        serieTop.forEach(s -> System.out.println(s.getTitulo()
                + "\nAvaliação: " + s.getAvaliacao()));
    }

    private void buscarSeriesPorCategoria() {
        System.out.println("Digite uma categoria/gênero para buscar séries: ");
        var nomeGenero = input.nextLine();
        Categoria categoria = Categoria.fromPortugues(nomeGenero);

        List<Serie> seriesPorCategoria = repositorio.findByGenero(categoria);

        System.out.println("==Séries da categoria " + categoria+ "==");
        seriesPorCategoria.forEach(System.out::println);
    }

    private void buscarSeriesPorNumeroDeTemporadasEAvaliacao() {
        System.out.println("Digite o número máximo de temporadas que a série deve ter: ");
        var numeroDeTemporadas = input.nextInt();

        System.out.print("Digite uma avaliação minima para busca: ");
        var avaliacaoMinima = input.nextDouble();

        List<Serie> seriesEncontradas = repositorio.seriesPorTemporadaEAvaliacao(numeroDeTemporadas, avaliacaoMinima );

        System.out.println("==Séries com " +numeroDeTemporadas+
                " ou menos temporadas e avaliação maior ou igual a " + avaliacaoMinima);

        seriesEncontradas.forEach(s -> System.out.println(s.getTitulo()+
                "\nTotal de temporadas: " +s.getTotalTemporadas()+
                "\nAvaliação: " +s.getAvaliacao()));
    }

    private void buscarEpisodioPorTrecho() {
        System.out.print("Digite o nome do episodio para busca: ");
        var trechoEpisodio = input.nextLine();
    }

        /*System.out.println("*** MENU DA ORBIT STREAM ***");
        System.out.print("Digite o nome da série para busca: ");
        var nomeSerie = input.nextLine();
        var json = consumoApi.buscarDados( ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);

        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dados);

        List<DadosTemporada> temporadas = new ArrayList<>();

        for (int i = 1; i <= dados.totalTemporadas(); i++) {
            var jsonTemporadas = consumoApi.buscarDados(ENDERECO + nomeSerie.replace(" ", "+") + "&Season=" + i + API_KEY);

            DadosTemporada dadosTemporada = conversor.obterDados(jsonTemporadas, DadosTemporada.class);

            temporadas.add(dadosTemporada);
        }

        temporadas.forEach(System.out::println);

        for(int i = 0; i < dados.totalTemporadas(); i++){
            List<DadosEpisodio> episodiosTemporada = temporadas.get(i).listaDeEpisodio();

            for(int j = 0; j < episodiosTemporada.size(); j++) {
                System.out.println(episodiosTemporada.get(j).titulo());
            }
        }

        temporadas.forEach(t -> t.listaDeEpisodio().forEach(e -> System.out.println(e.titulo())));
        temporadas.forEach(System.out::println);

        List<DadosEpisodio> listaEpisodios = temporadas.stream()
                .flatMap(t -> t.listaDeEpisodio().stream())
                .collect(Collectors.toList());

        System.out.println("\nTop 10 episodios: ");
        listaEpisodios.stream()
                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
                .peek(e -> System.out.println("Primeiro Filtro(N/A) " + e))
                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
                .peek(e -> System.out.println("Ordenação " + e))
                .limit(10)
                .peek(e -> System.out.println("Limite " + e))
                .map(e -> e.titulo().toUpperCase())
                .peek(e -> System.out.println("Mapeamento " + e))
                .forEach(System.out::println);

        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.listaDeEpisodio().stream()
                        .map(d -> new Episodio(t.numeroTemporada(), d))
                ).collect(Collectors.toList());


        episodios.forEach(System.out::println);


        System.out.print("Digite um trecho do titulo de algum episódio para pesquisa: ");
        var trechoTitulo = input.nextLine();

        Optional<Episodio> resultadoBuscaTrecho = episodios.stream()
                .filter(e -> e.getTitulo().toUpperCase().contains(trechoTitulo.toUpperCase()))
                .findFirst();

        if (resultadoBuscaTrecho.isPresent()) {
            System.out.println("Episódio encontrado!");
            System.out.println("Temporada: " +resultadoBuscaTrecho.get().getTemporada() );
        } else {
            System.out.println("Episódio não encontrado!");
        }

        System.out.print("A partir de que ano você deseja ver os episodios? ");
        var ano = input.nextInt();
        input.nextLine();

        LocalDate dataBusca = LocalDate.of(ano, 1, 1);

        DateTimeFormatter formattter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        episodios.stream()
                .filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataBusca))
                .forEach(e -> System.out.println(
                        "Temporada: " +e.getTemporada() +
                                "\nEpisódio: " +e.getTitulo() +
                                        "\nData de lançamento: " +e.getDataLancamento().format(formattter)
                ));

        Map<Integer, Double> avaliacoesPorTemporada = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.groupingBy(Episodio::getTemporada,
                        Collectors.averagingDouble(Episodio::getAvaliacao)));

        System.out.println(avaliacoesPorTemporada);

        DoubleSummaryStatistics est = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.summarizingDouble(Episodio::getAvaliacao));

        System.out.println("Média: " +est.getAverage());
        System.out.println("Pior Episodio: " +est.getMin());
        System.out.println("Melhor Episodio: " +est.getMax());
        System.out.println("Quantidade de episódios avaliados: " +est.getCount());
*/
}

