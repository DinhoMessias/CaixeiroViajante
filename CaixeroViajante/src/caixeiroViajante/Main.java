package caixeiroViajante;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		int[][] pontos = Util.carregarPontos("15.txt");
		ArrayList<Cidade> cidades = Util.carregarCidades(pontos);
		Rota r1 = new Rota();
		r1 = Util.gerarRotaRandom(cidades, 5);

		

	}

}
