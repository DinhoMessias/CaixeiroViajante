package caixeiroViajante;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		ArrayList<Cidade> cidadesCarregadas = Util.carregarCidades("15.txt");
		ArrayList<Cidade>cidadesRota = Util.gerarRota(cidadesCarregadas, 7);
	}

}
