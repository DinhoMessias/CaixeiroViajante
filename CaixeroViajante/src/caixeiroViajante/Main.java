package caixeiroViajante;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		ArrayList<Cidade> cidadesCarregadas = Util.carregarCidades("324.txt");
		// Scanner entrada = new Scanner(System.in);
		int tamPopulacao = 20;
		int tamRota = 20;
		int contLoop = 0;
		int cont = 0;
		Rota melhorRotaInicial = new Rota(tamRota);

		// Metodo de selecao por Fitness Medio
		Populacao pop01 = new Populacao();
		Populacao pais01 = new Populacao();
		Rota filho01 = new Rota(tamRota);
		Rota melhorRota01 = new Rota(tamRota);
		int numGeracaoPop01 = 0;
		int numLoops01 = 0;
		long min01 = 0;

		// Metodo selecao proporcional
		Populacao pop02 = new Populacao();
		Populacao pais02 = new Populacao();
		Rota filho02 = new Rota(tamRota);
		Rota melhorRota02 = new Rota(tamRota);
		int numGeracaoPop02 = 0;
		int numLoops02 = 0;
		long min02 = 0;

		while (cont != 5) {
			cont++;
			// Gerando populacao inicial aleatoria
			pop01 = Util.gerarPopulacao(cidadesCarregadas, tamPopulacao, tamRota);
			// Clonando populacao
			pop02.clonarPopulacao(pop01);

			melhorRotaInicial = pop01.getMelhorRota();

			// System.out.println("\n" + pop01.toString());
			System.out.println("Loop: " + cont);
			System.out.println("Fitness Medio Inicial: " + Selecao.calcularFitnessMedio(pop01));
			System.out.println("Melhor Rota Inicial: " + melhorRotaInicial.toString());
			melhorRota01 = pop01.getMelhorRota();
			melhorRota02 = pop02.getMelhorRota();
			// Condicao Parada
			long tempoInicial = System.currentTimeMillis();
			// Iniciando ciclo do Algoritmo Genetico
			do {
				contLoop++;
				// Selecionando Pais pelo Fitness Medio
				pais01 = Selecao.selecaoRoletaPorBilhete(pop01);
				// Selecionando Pais proporcionalmente
				pais02 = Selecao.selecaoRoletaProporcional(pop02);

				// Gerarando filhos por PMX
				filho01 = Cruzamento.cruzamentoPMX(pais01);
				filho02 = Cruzamento.cruzamentoPMX(pais02);

				// Chamando metodo de mutacao
				Mutacao.mutacaoTroca(filho01);
				Mutacao.mutacaoTroca(filho02);

				if (filho01.getFitness() < melhorRota01.getFitness()) {
					numLoops01 = contLoop;
					melhorRota01 = filho01;
					min01 = ((System.currentTimeMillis() - tempoInicial) / 60000);
				}
				if (filho02.getFitness() < melhorRota02.getFitness()) {
					numLoops02 = contLoop;
					melhorRota02 = filho02;
					min02 = ((System.currentTimeMillis() - tempoInicial) / 60000);
				}

				// Detectar se o filho não é clone de nenhum cromossomo que ja exista na
				// populacao Caso o filho nao seja clone ele entra no metodo de reinsercao
				if (!pop01.isClone(filho01)) {
					Reinsercao.reinsercaoMelhorFitPais(pop01, pais01, filho01);
				}
				if (!pop02.isClone(filho02)) {
					Reinsercao.reinsercaoMelhorFitPais(pop02, pais02, filho02);
				}

				numGeracaoPop01 = pop01.getMaiorGeracaoPop();
				numGeracaoPop02 = pop02.getMaiorGeracaoPop();
			} while (((System.currentTimeMillis() - tempoInicial) / 60000) <= 60);
			System.out.println("\nO algoritmo rodou: " + contLoop + " vezes");

			System.out.println("\nMelhor Solução Encontrada Selecao Fitness Medio: " + melhorRota01.toString());
			System.out.println("Loops ate encontrar melhor solucao: " + numLoops01);
			System.out.println("Tempo ate encontrar melhor solucao: " + min01 + " minutos");
			System.out.println("Fitness Medio Final Selecao Fitness Medio: " + Selecao.calcularFitnessMedio(pop01));
			System.out.println("O algoritmo chegou até a " + numGeracaoPop01 + " geracao com Selecao Fitness Medio");

			System.out.println("\nMelhor Solução Encontrada Selecao Proporcional: " + melhorRota02.toString());
			System.out.println("Loops ate encontrar melhor solucao: " + numLoops02);
			System.out.println("Tempo ate encontrar melhor solucao: " + min02 + " minutos");
			System.out.println("Fitness Medio Final Selecao Proporcional: " + Selecao.calcularFitnessMedio(pop02));
			System.out.println("O algoritmo chegou até a " + numGeracaoPop02 + " geracao com Selecao Proporcional\n");
		}
	}
}
