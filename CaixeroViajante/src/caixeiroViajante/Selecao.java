package caixeiroViajante;

import java.util.Random;

public class Selecao {
	public static Populacao selecaoRoletaPorBilhete(Populacao pop) {
		Populacao pais = new Populacao();
		int numTotalBilhetes = 0;
		int numSelecionado = 0;
		Random random = new Random();

		calChanceRoleta(pop);

		for (Rota r1 : pop.getPop()) {
			numTotalBilhetes += r1.getAptidao();
		}

		while (numSelecionado != 2) {
			double sumAptd = 0;
			int numRoleta = random.nextInt(numTotalBilhetes + 1);
			for (Rota r2 : pop.getPop()) {
				sumAptd += r2.getAptidao();
				if (sumAptd >= numRoleta && !pais.getPop().contains(r2)) {
					pais.addRota(r2);
					numSelecionado++;
					break;
					// garantindo que o algoritmo nao selecione o mesmo cromossomo
				} else if (sumAptd >= numRoleta && pais.getPop().contains(r2)) {
					break;
				}
			}
		}
		return pais;
	}

	public static void calChanceRoleta(Populacao pop) {
		Random random = new Random();
		double fitMedio = calcularFitnessMedio(pop);
		/*
		 * caso o fitness do cromossomo seja maior que a media da populacao ele recebe
		 * no maximo 5 bilhetes para roleta, e se o fitness for menor que a media da
		 * populacao o cromossomo recebe de 6 ate 10 bilhetes
		 */
		for (int i = 0; i < pop.getPop().size(); i++) {
			if (pop.getPop().get(i).getFitness() > fitMedio) {
				pop.getPop().get(i).setAptidao(random.nextInt((5 - 1) + 1) + 1);
			} else {
				pop.getPop().get(i).setAptidao(random.nextInt((10 - 6) + 1) + 6);
			}
		}
	}

	public static double calcularFitnessMedio(Populacao pop) {
		double sumFit = 0;

		for (Rota rota : pop.getPop()) {
			sumFit += rota.getFitness();
		}
		return (sumFit / pop.getPop().size());
	}
}
