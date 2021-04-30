package caixeiroViajante;

import java.util.ArrayList;

public class Populacao {
	private ArrayList<Rota> pop;

	public Populacao(ArrayList<Rota> pop) {
		this.pop = pop;
	}

	public Populacao() {

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

	public Rota getMelhorRota() {
		Rota melhorRota = new Rota();
		Double fitness = Double.MAX_VALUE;
		for (Rota rota : this.pop) {
			if (rota.getFitness() < fitness) {
				melhorRota = rota;
				fitness = rota.getFitness();
			}
		}
		return melhorRota;
	}
}
