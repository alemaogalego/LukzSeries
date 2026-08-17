package br.com.lucas.lukzseries.principal;

import br.com.lucas.lukzseries.model.DadosEpisodio;
import br.com.lucas.lukzseries.model.DadosSeries;
import br.com.lucas.lukzseries.model.DadosTemporada;
import br.com.lucas.lukzseries.model.Episodio;
import br.com.lucas.lukzseries.service.ConsumoApi;
import br.com.lucas.lukzseries.service.ConverteDados;

import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();

    private final String ENDERECO= "http://www.omdbapi.com/?t=";
    private final String API_KEY= "&apiKey=3eb9ae48";

    public void exibirMenu() {
        System.out.println("=== Menu ===");
        System.out.println("Buscar o nome da série: ");
        var nomeSerie = leitura.nextLine();

        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);

        DadosSeries dados = conversor.obterDados(json, DadosSeries.class);
        System.out.println(dados);

        List<DadosTemporada> temporadas = new ArrayList<>();

		for (int i = 1; i <= dados.totalDeTemporadas(); i++) {
			json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + "&Season=" + i + API_KEY);
			DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}
		temporadas.forEach(System.out::println);

        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e)));

        List<DadosEpisodio> dadosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                .toList();


//        System.out.println("\n=== Top 5 episódios com melhor avaliação ===");
//        dadosEpisodios.stream()
//                .filter(e -> !e.avaliacao().equals("N/A"))
//                .peek(e -> System.out.println("Filtro N/A " + e))
//                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
//                .peek(e -> System.out.println("Ordenação " + e))
//                .limit(5)
//                .peek(e -> System.out.println("Limite 5 " + e))
//                .map(e -> e.titulo().toUpperCase())
//                .peek(e -> System.out.println("Mapeamento " + e))
//                .forEach(System.out::println);

        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                .map(d -> new Episodio(t.numero(), d)))
                .toList();
//
        episodios.forEach(System.out::println);

//        System.out.println("Digite o nome do episodio: ");
//        var nomeEpisodio = leitura.nextLine();
//
//        Optional<Episodio> episodioBuscado = episodios.stream()
//                .filter(e -> e.getTitulo().toLowerCase().contains(nomeEpisodio.toLowerCase()))
//                .findFirst();
//        if (episodioBuscado.isPresent()) {
//            System.out.println("Episódio encontrado!");
//            System.out.println("Temporada: " + episodioBuscado.get().getTemporada());
//            System.out.println("Episódio: " + episodioBuscado.get().getNumeroEpisodio());
//
//        } else {
//            System.out.println("Episódio não encontrado.");
//        }

        Map<Integer, Double> avaliacoesPorTemporada = episodios.stream()
                .filter(e -> e.getAvaliacao()> 0.0)
                .collect(Collectors.groupingBy(Episodio::getTemporada, Collectors.averagingDouble(Episodio::getAvaliacao)));
        System.out.println("\n=== Média de avaliação por temporada ===");
        System.out.println(avaliacoesPorTemporada);

        DoubleSummaryStatistics est = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.summarizingDouble(Episodio::getAvaliacao));
        System.out.println(est);
        System.out.println("\n=== Estatísticas de avaliação ===");
        System.out.println("Média: " + est.getAverage());
        System.out.println("Melhor episódio: " + est.getMax());
        System.out.println("Pior episódio: " + est.getMin());
        System.out.println("Quantidade: " + est.getCount());

//
//        System.out.println("Digite o ano para buscar os episódios lançados a partir dele: ");
//        var ano = leitura.nextInt();
//        leitura.nextLine(); //
//
//
//        LocalDate dataBusca = LocalDate.of(ano, 1,1);
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//
//
//        episodios.stream()
//                .filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataBusca))
//                .forEach(e -> System.out.println(
//                        "Temporada: " + e.getTemporada() +
//                                " - Episódio: " + e.getNumeroEpisodio() +
//                                " - Data Lancamento: " + e.getDataLancamento().format(formatter)
//
//                ));
    }
}

