package caixeiroViajante;

import java.util.Arrays;

public class Cidade {

	private int[] coordenadas;

	public Cidade(int[] coordenadas) {
		this.coordenadas = coordenadas;
	}

	public Cidade() {
		this.coordenadas = new int[2];
	}

	public int[] getCoordenadas() {
		return coordenadas;
	}

	public void setCoordenadas(int[] coordenadas) {
		this.coordenadas = coordenadas;
	}

	public double calcularDistancia(int[] coordenada) {
		double distancia;

		int x = (int) Math.pow((this.coordenadas[0] - coordenada[0]), 2);
		int y = (int) Math.pow((this.coordenadas[1] - coordenada[1]), 2);
		distancia = Math.sqrt(x + y);

		return distancia;
	}

	public String toString() {
		return "Coordenadas: " + Arrays.toString(coordenadas);
	}

}
