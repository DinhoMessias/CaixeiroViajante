package caixeiroViajante;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		ArrayList<Cidade> cidadesCarregadas = Util.carregarCidades("324.txt");
		Scanner entrada = new Scanner(System.in);
		Populacao populacao = new Populacao();
		Populacao pais = new Populacao();
		int tamPopulacao = 10;
		int tamRota = 9;
		int contLoop = 0;
		Rota filho = new Rota(tamRota);
		Rota melhorRota = new Rota(tamRota);
		long tempoInicial = System.currentTimeMillis();
		double melhorFit = 0.0;

		/*
		 * System.out.println("Informe o numero de cidades da rota: "); tamRota =
		 * entrada.nextInt();
		 * System.out.println("Informe o tamanho da populacao inicial: "); tamPopulacao
		 * = entrada.nextInt(); entrada.close();
		 */

		populacao = Util.gerarPopulacao(cidadesCarregadas, tamPopulacao, tamRota);
		melhorRota = populacao.getMelhorRota();
		melhorFit = Selecao.calcularFitnessMedio(populacao);

		System.out.println("\n" + populacao.toString());
		System.out.println("Fitness Medio: " + Selecao.calcularFitnessMedio(populacao));
		System.out.println("Melhor Rota: " + melhorRota.toString());

		// Iniciando ciclo do Algoritmo Genetico
		do {
			contLoop++;
			// Selecionando Pais
			pais = Selecao.selecaoRoletaPorBilhete(populacao);
			// System.out.println("\nPais escolhidos:\n" + pais.toString());

			// Gerarando filho por Mascara
			filho = Cruzamento.cruzamentoPMX(pais);
			// System.out.println(filho.toString());

			// Chamando metodo de mutacao
			Mutacao.mutacaoTroca(filho);
			// System.out.println(filho.toString());

			// Detectar se o filho não é clone de nenhum cromossomo que ja exista na
			// populacao Caso o filho nao seja clone ele entra no metodo de reinsercao
			if (!Util.isClone(populacao, filho.getRota())) {
				Util.reinsercaoMelhorFitPais(populacao, pais, filho);
			}

		} while (((System.currentTimeMillis() - tempoInicial) / 60000) <= 5);

		System.out.println("\nPopulacao: \n" + populacao.toString());
		System.out.println("\nMelhor Solução Encontrada Reinsercao: " + populacao.getMelhorRota().toString());
		System.out.println("Fitness Medio Final: " + Selecao.calcularFitnessMedio(populacao));
		System.out.println("O algoritmo precisou de " + contLoop + " para encontrar a melhor solucao");

	}

}
