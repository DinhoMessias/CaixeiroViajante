package caixeiroViajante;

import java.util.Arrays;

public class Cidade {

	private int[] coordenadas;
	private String label;

	public Cidade(int[] coordenadas, String label) {
		this.coordenadas = coordenadas;
		this.label = label;
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

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public double calcularDistancia(int[] coordenada) {
		double distancia;

		int x = (int) Math.pow((this.coordenadas[0] - coordenada[0]), 2);
		int y = (int) Math.pow((this.coordenadas[1] - coordenada[1]), 2);
		distancia = Math.sqrt(x + y);

		return distancia;
	}

	public String toString() {
		return label + ": " + Arrays.toString(coordenadas);
	}

}
