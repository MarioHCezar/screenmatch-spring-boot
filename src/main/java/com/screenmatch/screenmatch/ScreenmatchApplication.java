package com.screenmatch.screenmatch;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.screenmatch.screenmatch.model.DadosEpisodio;
import com.screenmatch.screenmatch.model.DadosSerie;
import com.screenmatch.screenmatch.model.DadosTemporada;
import com.screenmatch.screenmatch.service.ConsumoApi;
import com.screenmatch.screenmatch.service.ConverteDados;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var consumoApi = new ConsumoApi();
		var json = consumoApi
				.obterDados("http://www.omdbapi.com/?apikey=d73f361a&t=Better_Call_Saul");

		System.out.println(json);
		var conversor = new ConverteDados();
		DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
		System.out.println(dados);
		json = consumoApi
				.obterDados("http://www.omdbapi.com/?apikey=d73f361a&t=Better_Call_Saul");
		dados = conversor.obterDados(json, DadosSerie.class);
		System.out.println(dados);
		DadosEpisodio dadosEpisodio = conversor.obterDados(json, DadosEpisodio.class);
		System.out.println(dadosEpisodio);
		json = consumoApi
				.obterDados("http://www.omdbapi.com/?apikey=d73f361a&t=Better_Call_Saul&season=6");
		// DadosTemporada dadosTemporada = conversor.obterDados(json,
		// DadosTemporada.class);
		// System.out.println(dadosTemporada);

		List<DadosTemporada> temporadas = new ArrayList<>();

		for (int i = 1; i <= dados.totalTemporadas(); i++) {
			json = consumoApi
					.obterDados("http://www.omdbapi.com/?apikey=d73f361a&t=Better_Call_Saul&season=" + i + "");
			DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}
		temporadas.forEach(System.out::println);
	}

}
