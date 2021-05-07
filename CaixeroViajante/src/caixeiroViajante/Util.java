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
		ArrayList<Cidade> cidades = new ArrayList<Cidade>();

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
				String label = "Pt " + i;
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

	public static Rota gerarRota(ArrayList<Cidade> cidades, int numCidadesRota) {
		Rota rota = new Rota(numCidadesRota);
		ArrayList<Cidade> cidadesEscolhidas = new ArrayList<>();
		Cidade[] cidadesRota = new Cidade[numCidadesRota];
		Random random = new Random();

		while (cidadesEscolhidas.size() != numCidadesRota) {
			int index = random.nextInt(cidades.size());
			if (!cidadesEscolhidas.contains(cidades.get(index))) {
				cidadesEscolhidas.add(cidades.get(index));
			}
		}

		for (int i = 0; i < cidadesEscolhidas.size(); i++) {
			cidadesRota[i] = cidadesEscolhidas.get(i);
		}
		rota.setRota(cidadesRota);
		rota.calcularFitness();
		return rota;
	}

	public static Populacao gerarPopulacao(ArrayList<Cidade> cidades, int tamPopulacao, int numCidadesRota) {
		Populacao populacao = new Populacao();
		Rota rota = gerarRota(cidades, numCidadesRota);
		//Definindo a geracao inicial como 1
		rota.setGeracao(01);
		populacao.addRota(rota);

		while (populacao.getPop().size() != tamPopulacao) {
			rota = shuffleRota(rota, numCidadesRota);
			if (!isClone(populacao, rota.getRota())) {
				populacao.addRota(rota);
			}
		}
		return populacao;
	}

	public static Rota shuffleRota(Rota rota, int numCidadesRota) {
		Rota rotaFinal = new Rota(numCidadesRota);
		ArrayList<Cidade> rotaAlterada = new ArrayList<Cidade>();

		for (Cidade cidade : rota.getRota()) {
			rotaAlterada.add(cidade);
		}
		Collections.shuffle(rotaAlterada);
		for (int i = 0; i < rotaAlterada.size(); i++) {
			rotaFinal.getRota()[i] = rotaAlterada.get(i);
		}
		rotaFinal.setGeracao(rota.getGeracao());
		rotaFinal.calcularFitness();
		return rotaFinal;
	}

	public static Boolean isClone(Populacao pop, Cidade[] rota) {
		int cont = 0;

		for (Rota r1 : pop.getPop()) {
			for (int i = 0; i < rota.length; i++) {
				if (rota[i].getLabel() == r1.getRota()[i].getLabel()) {
					cont++;
				}
			}
			if (cont == rota.length) {
				return true;
			}
			cont = 0;
		}
		return false;
	}

	public static void reinsercaoMelhorFitPais(Populacao populacao, Populacao pais, Rota filho) {
		Rota piorPai = new Rota(pais.getPop().get(0).getSizeRota());
		double fitMelhorPai = Double.MAX_VALUE;
		double piorFitPais = Double.MIN_VALUE;
		int cont = 0;

		for (Rota rota : pais.getPop()) {
			if (rota.getFitness() > piorFitPais) {
				piorFitPais = rota.getFitness();
				piorPai = rota;
			}
			if (rota.getFitness() < fitMelhorPai) {
				fitMelhorPai = rota.getFitness();
			}
		}

		if (filho.getFitness() < fitMelhorPai) {
			cont = populacao.getPop().indexOf(piorPai);
			populacao.getPop().set(cont, filho);
		}
	}
}
