package caixeiroViajante;

import java.util.ArrayList;
import java.util.Random;

public class Cruzamento {

	public static Rota cruzamentoPMX(Populacao pais) {
		Random random = new Random();
		Rota filho01 = new Rota(pais.getPop().get(0).getSizeRota());
		Rota filho02 = new Rota(pais.getPop().get(0).getSizeRota());
		ArrayList<Cidade> mapFilho1 = new ArrayList<Cidade>();
		ArrayList<Cidade> mapFilho2 = new ArrayList<Cidade>();
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
			filho02.addCidadeRota(pais.getPop().get(0).getCidade(i), i);
			// Cidades do pai 2 que foram adicionadas ao filho 1
			mapFilho1.add(pais.getPop().get(1).getRota()[i]);
			// Cidades do pai 1 que foram adicionadas ao filho 2
			mapFilho2.add(pais.getPop().get(0).getRota()[i]);
		}

		preencherFilhosPtCorte1(pais, filho01, filho02, mapFilho1, mapFilho2, ptCorte1);
		preencherFilhoPtCorte2(pais, filho01, filho02, mapFilho1, mapFilho2, ptCorte2);

		if (filho01.getFitness() > filho02.getFitness()) {
			return filho02;
		} else {
			return filho01;
		}
	}

	private static void preencherFilhosPtCorte1(Populacao pais, Rota filho01, Rota filho02, ArrayList<Cidade> mapFilho1,
			ArrayList<Cidade> mapFilho2, int ptCorte1) {
		// filho 1 e 2 é preenchido com cópia dos elementos do pai 1 e 2 respectivamente
		// ate o primeiro ponto de corte
		for (int i = 0; i < ptCorte1; i++) {
			// se a cidade do pai01 nao estiver entre as cidades que foram pegas do pai02
			// ele adicona ela ao filho01
			Cidade c1 = pais.getPop().get(0).getCidade(i);
			Cidade c2 = pais.getPop().get(1).getCidade(i);
			if (!mapFilho1.contains(c1)) {
				filho01.addCidadeRota(c1, i);
			} else {
				// caso esteja tentando adicionar uma cidade ja visitada na rota ele pega no
				// mapeamento do pai 1 a cidade que foi trocada com o pai 2
				int index = mapFilho1.indexOf(pais.getPop().get(0).getCidade(i));
				filho01.addCidadeRota(mapFilho2.get(index), i);
			}
			// se a cidade do pai02 nao estiver entre as cidades que foram pegas do pai01
			// ele adicona ela ao filho02
			if (!mapFilho2.contains(c2)) {
				filho02.addCidadeRota(c2, i);
			} else {
				// caso esteja tentando adicionar uma cidade ja visitada na rota ele pega no
				// mapeamento do pai 2 a cidade que foi trocada com o pai 1
				int index = mapFilho2.indexOf(c2);
				filho02.addCidadeRota(mapFilho1.get(index), i);
			}
		}
	}

	private static void preencherFilhoPtCorte2(Populacao pais, Rota filho01, Rota filho02, ArrayList<Cidade> mapFilho1,
			ArrayList<Cidade> mapFilho2, int ptCorte2) {

		for (int i = ptCorte2; i < filho01.getSizeRota(); i++) {
			// se a cidade do pai01 nao estiver entre as cidades que foram pegas do pai02
			// ele adicona ela ao filho01
			Cidade c1 = pais.getPop().get(0).getCidade(i);
			Cidade c2 = pais.getPop().get(1).getCidade(i);
			if (!mapFilho1.contains(c1)) {
				filho01.addCidadeRota(c1, i);
			} else {
				// caso esteja tentando adicionar uma cidade ja visitada na rota ele pega no
				// mapeamento do pai 1 a cidade que foi trocada com o pai 2
				int index = mapFilho1.indexOf(pais.getPop().get(0).getCidade(i));
				filho01.addCidadeRota(mapFilho2.get(index), i);
			}
			// se a cidade do pai02 nao estiver entre as cidades que foram pegas do pai01
			// ele adicona ela ao filho02
			if (!mapFilho2.contains(c2)) {
				filho02.addCidadeRota(c2, i);
			} else {
				// caso esteja tentando adicionar uma cidade ja visitada na rota ele pega no
				// mapeamento do pai 2 a cidade que foi trocada com o pai 1
				int index = mapFilho2.indexOf(c2);
				filho02.addCidadeRota(mapFilho1.get(index), i);
			}
		}
		filho01.calcularFitness();
		filho02.calcularFitness();
	}

}
