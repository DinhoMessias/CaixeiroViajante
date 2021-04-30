package caixeiroViajante;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Util {

	public static ArrayList<Cidade> carregarCidades(String caminho) {
		FileReader arq;
		BufferedReader buff;
		String saida = "";
		ArrayList<Cidade> cidades = new ArrayList();

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

			for (int i = 0; i < pontos.length; i++) {
				int[] coord = new int[2];
				int pos = 0;
				String label = "Ponto " + i;
				for (int j = 0; j < 2; j++) {
					coord[pos] = pontos[i][j];
					pos++;
				}
				Cidade c1 = new Cidade(coord, label);
				cidades.add(c1);
			}

			buff.close();
			arq.close();
			return cidades;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Erro na abertura do arquivo!");
			return null;
		}
	}

	public static ArrayList<Cidade> gerarRota(ArrayList<Cidade> cidades, int numCidades) {
		ArrayList<Cidade> rota = new ArrayList<>();
		Random random = new Random();

		while (rota.size() != numCidades) {
			int index = random.nextInt(cidades.size());
			if (!rota.contains(cidades.get(index))) {
				rota.add(cidades.get(index));
			}
		}
		return rota;
	}

	public static Rota vizinhoProximo(ArrayList<Cidade> cidadesRota) {
		ArrayList<Cidade> cidadesVisitadas = new ArrayList<>();
		Random random = new Random();
		// selecionando um ponto aleatorio para comecar a rota
		Cidade cidade = cidadesRota.get(random.nextInt(cidadesRota.size()));
		Cidade cidadeProx = new Cidade();
		double distancia = Double.MAX_VALUE;
		// Adicionando o ponto aleatorio no array de cidadesVisitadas
		cidadesVisitadas.add(cidade);

		while (cidadesVisitadas.size() != cidadesRota.size()) {
			// Percorro todo o array da rota procurando a cidade mais proxima da inicial
			for (int i = 0; i < cidadesRota.size(); i++) {
				if (cidade.calcularDistancia(cidadesRota.get(i).getCoordenadas()) < distancia
						&& !cidadesVisitadas.contains(cidadesRota.get(i))) {
					// salvando a cidade mais proxima da cidade comparada
					cidadeProx = cidadesRota.get(i);
					// setando a distancia entra a cidade comparada e a mais proxima
					distancia = cidade.calcularDistancia(cidadesRota.get(i).getCoordenadas());
				}
			}
			// resetando o valor da distancia para o proximo ciclo de comparacao
			distancia = Double.MAX_VALUE;
			// pegando a cidade com a menor distancia comparada para o proximo ciclo de
			// comparacao
			cidade = cidadeProx;
			// adicionando a cidade mais proxima ao vetor das cidades visitadas
			cidadesVisitadas.add(cidadeProx);
		}
		// gerando a rota de retorno com o vetor das cidades visitadas e calculando o
		// fitness da rota
		Rota rota = new Rota();
		rota.setRota(cidadesVisitadas);
		rota.calcularFitness();

		return rota;
	}

}
