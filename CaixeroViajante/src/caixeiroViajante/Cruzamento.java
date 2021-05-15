package caixeiroViajante;

import java.util.ArrayList;
import java.util.Random;

public class Cruzamento {

	public static Rota cruzamentoPMX(Populacao pais) {
		Random random = new Random();
		Rota filho01 = new Rota(pais.getPop().get(0).getSizeRota());
		Rota filho02 = new Rota(pais.getPop().get(0).getSizeRota());
		ArrayList<Cidade> mapPai01 = new ArrayList<Cidade>();
		ArrayList<Cidade> mapPai02 = new ArrayList<Cidade>();
		// gerando pontos de corte aleatorios com o tamanho da rota - 1;
		int ptCorte1 = random.nextInt(pais.getPop().get(0).getSizeRota());
		int ptCorte2 = random.nextInt(pais.getPop().get(0).getSizeRota());
		// ordenando pontos de corte
		if (ptCorte1 > ptCorte2) {
			int x = ptCorte1;
			ptCorte1 = ptCorte2;
			ptCorte2 = x;
		}
		// mapeamento do primeiro pai é copiada no segundo filho, e mapeamento
		// do segundo pai é copiada para o primeiro filho,
		for (int i = ptCorte1; i < ptCorte2; i++) {
			filho01.addCidadeRota(pais.getPop().get(1).getCidade(i), i);
			// Cidades do pai 1 que foram adicionadas ao filho 2
			mapPai01.add(pais.getPop().get(0).getRota()[i]);

			filho02.addCidadeRota(pais.getPop().get(0).getCidade(i), i);
			// Cidades do pai 2 que foram adicionadas ao filho 1
			mapPai02.add(pais.getPop().get(1).getRota()[i]);
		}
		preencherFilhos(pais, filho01, filho02, mapPai01, mapPai02, ptCorte1, ptCorte2);

		return getMelhorFilho(filho01, filho02);
	}

	// Metodo que completa os filhos com as cidades de cada pai, calcula o fitness e
	// a geracao dos filhos
	private static void preencherFilhos(Populacao pais, Rota filho01, Rota filho02, ArrayList<Cidade> mapPai01,
			ArrayList<Cidade> mapPai02, int ptCorte01, int ptCorte02) {

		// Completando rota do filho01 com as cidades do pai01
		preencherFilho01PtCorte01(pais.getPop().get(0), filho01, mapPai01, mapPai02, ptCorte01);
		preencherFilho01PtCorte2(pais.getPop().get(0), filho01, mapPai01, mapPai02, ptCorte02);
		completarFilho(pais.getPop().get(0), filho01);

		// Completando rota do filho02 com as cidades do pai02
		preencherFilho2PtCorte01(pais.getPop().get(1), filho02, mapPai01, mapPai02, ptCorte01);
		preencherFilho2PtCorte2(pais.getPop().get(1), filho02, mapPai01, mapPai02, ptCorte02);
		completarFilho(pais.getPop().get(1), filho02);

		// Calcular geracao dos filhos e fitness de cada filho
		calcularGeracaoFilhos(pais, filho01, filho02);
		calcularFitnessFilhos(filho01, filho02);
	}

	// Metodo para completar o filho01 ate o primeiro ponto corte
	private static void preencherFilho01PtCorte01(Rota pai01, Rota filho01, ArrayList<Cidade> mapPai01,
			ArrayList<Cidade> mapPai02, int ptCorte01) {
		for (int i = 0; i < ptCorte01; i++) {
			// Copiando cidade do pai para adicionar ao filho
			Cidade cidade = pai01.getCidade(i);
			if (!filho01.contains(cidade)) {
				filho01.addCidadeRota(cidade, i);
			} else {
				// Buscando a cidade equivalente a cidade que ja esta na rota no mapeamento para
				// adicionar a rota
				int index = mapPai02.indexOf(cidade);
				cidade = mapPai01.get(index);
				if (!filho01.contains(cidade)) {
					filho01.addCidadeRota(cidade, i);
				} else {
					// Ciclo para percorrer todo mapeamento ate encontrar uma cidade equivalente que
					// nao esta na rota ainda
					boolean adicionado = false;
					// variavel de controle para evitar loop infinito
					int cont = 0;
					while (!adicionado && cont != mapPai01.size()) {
						// Caso a cidade ja se encontra na rota ele entra no mapeamento e procura a
						// cidade equivalente ate poder adicionar ela na rota garantindo que nao tenha
						// cidade repitida na rota
						if (!filho01.contains(cidade)) {
							filho01.addCidadeRota(cidade, i);
							adicionado = true;
						} else {
							index = mapPai02.indexOf(cidade);
							cidade = mapPai01.get(index);
							cont++;
						}
					}
				}
			}
		}
	}

	// Metodo para completar o filho01 do ponto de corte 2 ate o final do vetor
	private static void preencherFilho01PtCorte2(Rota pai01, Rota filho01, ArrayList<Cidade> mapPai01,
			ArrayList<Cidade> mapPai02, int ptCorte02) {
		for (int i = ptCorte02; i < filho01.getSizeRota(); i++) {
			// Copiando cidade do pai para adicionar ao filho
			Cidade cidade = pai01.getCidade(i);
			if (!filho01.contains(cidade)) {
				filho01.addCidadeRota(cidade, i);
			} else {
				int index = mapPai02.indexOf(cidade);
				cidade = mapPai01.get(index);
				if (!filho01.contains(cidade)) {
					filho01.addCidadeRota(cidade, i);
				} else {
					boolean adicionado = false;
					// variavel de controle para evitar loop infinito
					int cont = 0;
					while (!adicionado && cont != mapPai01.size()) {
						// Caso a cidade ja se encontra na rota ele entra no mapeamento e procura a
						// cidade equivalente ate poder adicionar ela na rota garantindo que nao tenha
						// cidade repitida na rota
						if (!filho01.contains(cidade)) {
							filho01.addCidadeRota(cidade, i);
							adicionado = true;
						} else {
							index = mapPai02.indexOf(cidade);
							cidade = mapPai01.get(index);
							cont++;
						}
					}
				}
			}
		}
	}

	// Metodo para completar filhos caso exista ainda exista cidades ainda nao
	// visitadas
	private static void completarFilho(Rota pai, Rota filho) {
		// Array com todas as posicoes que estao nulas no filho
		ArrayList<Integer> posNull = filho.getPosNull();
		int cont = 0;

		if (posNull.size() > 0) {
			for (int i = 0; i < pai.getSizeRota(); i++) {
				Cidade cidade = pai.getCidade(i);
				if (!filho.contains(cidade)) {
					filho.addCidadeRota(cidade, posNull.get(cont));
					cont++;
				}
			}
		}
	}

	// Metodo para completar o filho02 ate o primeiro ponto corte
	private static void preencherFilho2PtCorte01(Rota pai02, Rota filho02, ArrayList<Cidade> mapPai01,
			ArrayList<Cidade> mapPai02, int ptCorte01) {
		for (int i = 0; i < ptCorte01; i++) {
			// Copiando cidade do pai para adicionar ao filho
			Cidade cidade = pai02.getCidade(i);
			if (!filho02.contains(cidade)) {
				filho02.addCidadeRota(cidade, i);
			} else {
				// Buscando a cidade equivalente a cidade que ja esta na rota no mapeamento para
				// adicionar a rota
				int index = mapPai01.indexOf(cidade);
				cidade = mapPai02.get(index);
				if (!filho02.contains(cidade)) {
					filho02.addCidadeRota(cidade, i);
				} else {
					// Ciclo para percorrer todo mapeamento ate encontrar uma cidade equivalente que
					// nao esta na rota ainda
					boolean adicionado = false;
					// variavel de controle para evitar loop infinito
					int cont = 0;
					while (!adicionado && cont != mapPai02.size()) {
						// Caso a cidade ja se encontra na rota ele entra no mapeamento e procura a
						// cidade equivalente ate poder adicionar ela na rota garantindo que nao tenha
						// cidade repitida na rota
						if (!filho02.contains(cidade)) {
							filho02.addCidadeRota(cidade, i);
							adicionado = true;
						} else {
							index = mapPai01.indexOf(cidade);
							cidade = mapPai02.get(index);
							cont++;
						}
					}
				}
			}
		}
	}

	// Metodo para completar o filho02 do ponto de corte 2 ate o final do vetor
	private static void preencherFilho2PtCorte2(Rota pai02, Rota filho02, ArrayList<Cidade> mapPai01,
			ArrayList<Cidade> mapPai02, int ptCorte02) {
		for (int i = ptCorte02; i < filho02.getSizeRota(); i++) {
			// Copiando cidade do pai para adicionar ao filho
			Cidade cidade = pai02.getCidade(i);
			if (!filho02.contains(cidade)) {
				filho02.addCidadeRota(cidade, i);
			} else {
				int index = mapPai01.indexOf(cidade);
				cidade = mapPai02.get(index);
				if (!filho02.contains(cidade)) {
					filho02.addCidadeRota(cidade, i);
				} else {
					boolean adicionado = false;
					// variavel de controle para evitar loop infinito
					int cont = 0;
					while (!adicionado && cont != mapPai01.size()) {
						// Caso a cidade ja se encontra na rota ele entra no mapeamento e procura a
						// cidade equivalente ate poder adicionar ela na rota garantindo que nao tenha
						// cidade repitida na rota
						if (!filho02.contains(cidade)) {
							filho02.addCidadeRota(cidade, i);
							adicionado = true;
						} else {
							index = mapPai01.indexOf(cidade);
							cidade = mapPai02.get(index);
							cont++;
						}
					}
				}
			}
		}
	}

	// Metodo para calcular qual geracao do filhos gerados
	private static void calcularGeracaoFilhos(Populacao pais, Rota filho1, Rota filho2) {
		int geracao = 0;
		if (pais.getPop().get(0).getGeracao() == pais.getPop().get(1).getGeracao()) {
			geracao = pais.getPop().get(0).getGeracao() + 1;
			filho1.setGeracao(geracao);
			filho2.setGeracao(geracao);
			return;
		}
		if (pais.getPop().get(0).getGeracao() < pais.getPop().get(1).getGeracao()) {
			geracao = pais.getPop().get(0).getGeracao();
			filho1.setGeracao(geracao);
			filho2.setGeracao(geracao);
			return;
		} else {
			geracao = pais.getPop().get(1).getGeracao();
			filho1.setGeracao(geracao);
			filho2.setGeracao(geracao);
		}
	}

	// Metodo para calcular o fitness de cada filho
	private static void calcularFitnessFilhos(Rota filho1, Rota filho2) {
		filho1.calcularFitness();
		filho2.calcularFitness();
	}

	// Metodo que retorna filho com o menor fitness
	private static Rota getMelhorFilho(Rota filho01, Rota filho02) {
		if (filho01.getFitness() < filho02.getFitness()) {
			return filho01;
		} else {
			return filho02;
		}
	}
}
