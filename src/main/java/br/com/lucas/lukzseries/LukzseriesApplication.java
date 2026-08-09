package br.com.lucas.lukzseries;

import br.com.lucas.lukzseries.model.DadosEpisodio;
import br.com.lucas.lukzseries.model.DadosSeries;
import br.com.lucas.lukzseries.model.DadosTemporada;
import br.com.lucas.lukzseries.service.ConsumoApi;
import br.com.lucas.lukzseries.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class LukzseriesApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(LukzseriesApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var consumoApi = new ConsumoApi();
		var json = consumoApi.obterDados("http://www.omdbapi.com/?apikey=3eb9ae48&t=Gilmore+Girls");
//		System.out.println(json);

		ConverteDados conversor = new ConverteDados();

		DadosSeries dados = conversor.obterDados(json, DadosSeries.class);

		System.out.println(dados);

		json = consumoApi.obterDados("http://www.omdbapi.com/?apikey=3eb9ae48&t=Gilmore+Girls&Season=1&Episode=1");
		DadosEpisodio dadosEpisodio = conversor.obterDados(json, DadosEpisodio.class);
		System.out.println(dadosEpisodio);

		List<DadosTemporada> temporadas = new ArrayList<>();

		for (int i = 1; i <= dados.totalDeTemporadas(); i++) {
			json = consumoApi.obterDados("http://www.omdbapi.com/?apikey=3eb9ae48&t=Gilmore+Girls&Season=" + i);
			DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}
		temporadas.forEach(System.out::println);
	}


}
