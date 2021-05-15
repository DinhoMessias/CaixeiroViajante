package caixeiroViajante;

import java.util.Random;

public class Mutacao {
	public static void mutacaoTroca(Rota filho) {
		Random random = new Random();

		// Calcular porcentagem para saber se o cromossomo vai sofre mutacao
		int percTotal = random.nextInt(101);

		// Porcentagem de chance de acontecer uma mutaçao
		final int percentChanceMut = 3;

		// Numero de genes que vai sofre a mutacao
		int numGeneMut = (int) (0.05 * filho.getSizeRota());

		// garantindo que pelo menos 1 gene possa sofrer a mutacao
		if (numGeneMut <= 0) {
			numGeneMut = 2;
		}
		// Se porcentagem total for igual ou menor que a chance de acontecer mutacao o
		// algoritmo entra no ciclo para mudar a quantidade de genes definidos
		if (percTotal <= percentChanceMut) {
			for (int i = 0; i < numGeneMut; i++) {
				// Sorteando cidade que vai ser mutacao
				int indexMut = random.nextInt(filho.getRota().length);
				// Sorteando cidade para sofre alterada
				int indexTroca = random.nextInt(filho.getRota().length);
				// Escolhendo cidade para sofre mutacao
				Cidade cidadeTroca = filho.getCidade(indexMut);
				// Pegando cidade que vai ser alterada
				Cidade cidadeMut = filho.getCidade(indexTroca);
				// Trocando cidades de posicao
				filho.addCidadeRota(cidadeMut, indexMut);
				filho.addCidadeRota(cidadeTroca, indexTroca);
			}
			// recalculando o fitness caso o cromossomo sofra mutação
			filho.calcularFitness();
		}
	}
}
