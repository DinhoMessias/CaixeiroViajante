package caixeiroViajante;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;

public class Util {

	public static int[][] carregarPontos(String caminho) {
		FileReader arq;
		BufferedReader buff;
		String saida = "";

		try {
			arq = new FileReader(caminho);
			buff = new BufferedReader(arq);
			int pontos[][] = new int[Integer.parseInt(buff.readLine())][2];
			int lin = 0;
			String ponto = "";
			while (buff.ready()) {
				saida = buff.readLine();
				int col = 0;
				for (int i = 0; i < saida.length(); i++) {
					if (saida.charAt(i) != ' ') {
						ponto += saida.charAt(i);
					}
					if (saida.charAt(i) == ' ' || i == saida.length() - 1) {
						int p = Integer.parseInt(ponto);
						ponto = "";
						pontos[lin][col] = p;
						col++;
					}
				}
				lin++;
			}
			buff.close();
			arq.close();
			return pontos;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Erro na abertura do arquivo!");
			return null;
		}
	}

	public static ArrayList<Cidade> carregarCidades(int[][] pontos) {
		ArrayList<Cidade> cidades = new ArrayList();

		for (int i = 0; i < pontos.length; i++) {
			int[] coord = new int[2];
			int pos = 0;
			for (int j = 0; j < 2; j++) {
				coord[pos] = pontos[i][j];
				pos++;
			}
			Cidade c1 = new Cidade(coord);
			cidades.add(c1);
		}

		return cidades;
	}

	public static Rota gerarRotaRandom(ArrayList<Cidade> cidades, int numCidades) {
		Rota rota = new Rota();
		Random random = new Random();

		while (rota.getRota().size() != numCidades) {
			int x = random.nextInt(cidades.size());
			rota.addCidadeRota(cidades.get(x));
		}

		rota.calcularFitness();
		return rota;
	}

}
