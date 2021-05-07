package caixeiroViajante;

import java.util.Random;

public class Rota {
	private int geracao;
	private Cidade[] rota;
	private Double fitness;
	private double aptidao;

	public Rota(int geracao, Cidade[] rota, Double fitness, int aptidao) {
		this.geracao = geracao;
		this.rota = rota;
		this.fitness = fitness;
		this.aptidao = aptidao;
	}

	public Rota(int tamRota) {
		this.rota = new Cidade[tamRota];
		this.fitness = 0.0;
		this.aptidao = 0;
	}

	public int getGeracao() {
		return geracao;
	}

	public void setGeracao(int geracao) {
		this.geracao = geracao;
	}

	public Cidade[] getRota() {
		return rota;
	}

	public void setRota(Cidade[] rota) {
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

	public void setAptidao(double aptidao) {
		this.aptidao = aptidao;
	}

	public void addCidadeRota(Cidade cidade, int index) {
		this.rota[index] = cidade;
	}

	public void calcularFitness() {
		this.fitness = 0.0;
		for (int i = 0; i < this.rota.length; i++) {
			// calcula a distancia entre a 1ª cidades até a penultima da rota
			if (i < this.rota.length - 1) {
				fitness += this.rota[i].calcularDistancia(this.rota[i + 1].getCoordenadas());
			} else {
				// calcula a distancia da ultima cidade para a primeira da rota
				fitness += this.rota[i].calcularDistancia(this.rota[0].getCoordenadas());
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

	public Cidade getCidade(int index) {
		return this.rota[index];
	}

	public int getSizeRota() {
		return this.rota.length;
	}

	public boolean contains(Cidade cidade) {
		for (int i = 0; i < this.rota.length; i++) {
			if (this.rota[i].getLabel() == cidade.getLabel()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		String saida = "Rota: ";
		for (Cidade cidade : this.rota) {
			saida += cidade.getLabel() + " | ";
		}
		saida += this.rota[0].getLabel() + " | ";
		saida += "Fitness: " + this.fitness;

		return saida;
	}

}
