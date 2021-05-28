package caixeiroViajante;

import java.util.Random;

public class Selecao {
	public static Populacao selecaoRoletaPorBilhete(Populacao pop) {
		Populacao pais = new Populacao();
		int numTotalBilhetes = 0;
		int numSelecionado = 0;
		Random random = new Random();

		calChanceRoletaByFitMedio(pop);

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

	public static Populacao selecaoRoletaProporcional(Populacao pop) {
		Populacao pais = new Populacao();
		double numTotalBilhetes = 0;
		int numSelecionado = 0;
		Random random = new Random();

		calChanceRoletaProporcional(pop);

		while (numSelecionado != 2) {
			double sumAptd = 0;
			double numRoleta = random.nextDouble() * 100;
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

	public static void calChanceRoletaByFitMedio(Populacao pop) {
		double fitMedio = calcularFitnessMedio(pop);
		for (Rota rota : pop.getPop()) {
			rota.calcularAptidaoByMedia(fitMedio);
		}
	}

	public static void calChanceRoletaProporcional(Populacao pop) {
		double fitTotal = calcularFitnessTotal(pop);
		for (Rota rota : pop.getPop()) {
			rota.calcularAptidaoPropByFit(fitTotal);
		}
	}

	public static double calcularFitnessMedio(Populacao pop) {
		double sumFit = 0;
		for (Rota rota : pop.getPop()) {
			sumFit += rota.getFitness();
		}
		return (sumFit / pop.getPop().size());
	}

	public static double calcularFitnessTotal(Populacao pop) {
		double fitnessTotal = 0.0;
		for (Rota rota : pop.getPop()) {
			fitnessTotal += rota.getFitness();
		}
		return fitnessTotal;
	}

}
