package br.com.lucas.lukzseries;

import br.com.lucas.lukzseries.model.DadosSeries;
import br.com.lucas.lukzseries.service.ConsumoApi;
import br.com.lucas.lukzseries.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LukzseriesApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(LukzseriesApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var consumoApi = new ConsumoApi();
		var json = consumoApi.obterDados("http://www.omdbapi.com/?apikey=3eb9ae48&t=Gilmore+Girls");
		System.out.println(json);

		ConverteDados conversor = new ConverteDados();

		DadosSeries dados = conversor.obterDados(json, DadosSeries.class);

		System.out.println(dados);
		System.out.println("Título: " + dados.titulo());
		System.out.println("Total de Temporadas: " + dados.totalTemporada());
		System.out.println("Avaliação: " + dados.avaliacao());



	}

}
