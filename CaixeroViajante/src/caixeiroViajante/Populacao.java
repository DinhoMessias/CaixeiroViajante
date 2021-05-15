package caixeiroViajante;

import java.util.ArrayList;

public class Populacao {
	private ArrayList<Rota> pop;

	public Populacao(ArrayList<Rota> pop) {
		this.pop = pop;
	}

	public Populacao() {
		this.pop = new ArrayList<Rota>();
	}

	public ArrayList<Rota> getPop() {
		return pop;
	}

	public void setPop(ArrayList<Rota> pop) {
		this.pop = pop;
	}

	public void addRota(Rota rota) {
		this.pop.add(rota);
	}

	public int getMaiorGeracaoPop() {
		int maiorGeracao = Integer.MIN_VALUE;
		for (Rota rota : this.pop) {
			if (rota.getGeracao() > maiorGeracao) {
				maiorGeracao = rota.getGeracao();
			}
		}
		return maiorGeracao;
	}

	public Rota getMelhorRota() {
		Rota melhorRota = new Rota(this.pop.get(0).getRota().length);
		Double fitness = Double.MAX_VALUE;
		for (Rota rota : this.pop) {
			if (rota.getFitness() < fitness) {
				melhorRota = rota;
				fitness = rota.getFitness();
			}
		}
		return melhorRota;
	}

	public boolean isClone(Rota filho) {
		String rotaFilho = filho.imprimirRota();

		for (Rota rota : this.pop) {
			String rotaComparada = rota.imprimirRota();
			if (rotaFilho.equalsIgnoreCase(rotaComparada)) {
				return true;
			}
		}
		return false;
	}

	public void clonarPopulacao(Populacao populacao) {
		for (Rota rota : populacao.getPop()) {
			this.pop.add(rota);
		}
	}

	@Override
	public String toString() {
		String saida = "";

		for (Rota rota : pop) {
			saida += rota.toString() + "\n";
		}

		return saida;
	}

}
