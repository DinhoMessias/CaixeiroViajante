package caixeiroViajante;

import java.util.ArrayList;
import java.util.Random;

public class Rota {
	private ArrayList<Cidade> rota;
	private Double fitness;
	private double aptidao;

	public Rota(ArrayList<Cidade> rota, Double fitness, int aptidao) {
		this.rota = rota;
		this.fitness = fitness;
		this.aptidao = aptidao;
	}

	public Rota() {
		this.rota = new ArrayList<Cidade>();
		this.fitness = 0.0;
		this.aptidao = 0;
	}

	public ArrayList<Cidade> getRota() {
		return rota;
	}

	public void setRota(ArrayList<Cidade> rota) {
		this.rota = rota;
	}

	public Double getFitness() {
		return fitness;
	}

	public void setFitness(Double fitness) {
		this.fitness = fitness;
	}

	public double getAptidao() {
		return aptidao;
	}

	public void setAptidao(int aptidao) {
		this.aptidao = aptidao;
	}

	public void addCidadeRota(Cidade cidade) {
		if (!this.rota.contains(cidade)) {
			this.rota.add(cidade);
		}
	}

	public void calcularFitness() {
		for (int i = 0; i < this.rota.size(); i++) {
			// calcula a distancia entre a 1ª cidades até a penultima da rota
			if (i < this.rota.size() - 1) {
				fitness += this.rota.get(i).calcularDistancia(this.rota.get(i + 1).getCoordenadas());
			} else {
				// calcula a distancia da ultima cidade para a primeira da rota
				fitness += this.rota.get(i).calcularDistancia(this.rota.get(0).getCoordenadas());
			}
		}
	}

	public void calcularAptidaoByMedia(double mediaFitness) {
		Random random = new Random();

		if (this.aptidao < mediaFitness) {
			this.aptidao = (random.nextInt((5 - 1) + 1) + 1);
		} else {
			this.aptidao = (random.nextInt((10 - 6) + 1) + 6);
		}
	}
	
	public void calcularAptidaoPropByFit(double fitTotal) {
		this.aptidao = (this.fitness * 100) / fitTotal;
	}
}
