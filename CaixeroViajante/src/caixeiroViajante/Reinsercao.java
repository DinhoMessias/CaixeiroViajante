package caixeiroViajante;

public class Reinsercao {
	public static void reinsercaoMelhorFitPais(Populacao populacao, Populacao pais, Rota filho) {
		Rota piorPai = new Rota(filho.getSizeRota());
		double fitMelhorPai = Double.MAX_VALUE;
		double piorFitPais = Double.MIN_VALUE;
		int cont = 0;

		for (Rota rota : pais.getPop()) {
			if (rota.getFitness() > piorFitPais) {
				piorFitPais = rota.getFitness();
				piorPai = rota;
			}
			if (rota.getFitness() < fitMelhorPai) {
				fitMelhorPai = rota.getFitness();
			}
		}

		if (filho.getFitness() < fitMelhorPai) {
			cont = populacao.getPop().indexOf(piorPai);
			populacao.getPop().set(cont, filho);
		}
	}
}
