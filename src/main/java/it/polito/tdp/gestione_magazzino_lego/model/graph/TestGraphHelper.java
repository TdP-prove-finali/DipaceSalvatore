package it.polito.tdp.gestione_magazzino_lego.model.graph;

import java.time.Year;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

import it.polito.tdp.gestione_magazzino_lego.model.Model;
import it.polito.tdp.gestione_magazzino_lego.model.bean.Color;
import it.polito.tdp.gestione_magazzino_lego.model.bean.Part;
import it.polito.tdp.gestione_magazzino_lego.model.bean.Set;
import it.polito.tdp.gestione_magazzino_lego.model.bean.Theme;
import it.polito.tdp.gestione_magazzino_lego.model.exception.LegoException;
import it.polito.tdp.gestione_magazzino_lego.model.ricorsione.RicorsioneResolverForGap;

public class TestGraphHelper extends GraphHelper {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TestGraphHelper tgr = new TestGraphHelper();

		List<Theme> themes = new ArrayList<Theme>();

		int couplingThreshold = 10;
		Graph<Set, DefaultEdge> graph = null;
		try {
			graph = tgr.creaGrafo(themes, couplingThreshold);
		} catch (LegoException le) {
			// TODO Auto-generated catch block
			le.printStackTrace();
		}

		List<Set> sets = null;
		try {
			sets = tgr.init(themes);
		} catch (LegoException e1) {
			// TODO Auto-generated catch block
			sets = Collections.emptyList();
		}
		
		Set source = sets.get(0);
		List<Set> visitaAmpiezza = tgr.visitaAmpiezza(graph, source);
		System.out.println("<VISITA AMPIEZZA>");
		for (Set sva : visitaAmpiezza) {
			System.out.println(sva);
		}

		Map<Set, List<Set>> neighboors = tgr.retrieveNeighbors(graph);
		for (Set s : neighboors.keySet()) {
			System.out.println("<" + s.getCode() + ">  --> " + neighboors.get(s));
		}

		// selezionare set sorgente per tirare fuori albero visita magari

		System.out.println("<ALBERO VISITA>");
		Map<Set, Set> alberoVisita = tgr.alberoVisita(graph, source);
		for (Set sav : alberoVisita.keySet()) {
			if (!sav.equals(source)) {
				System.out.format("%s <- %s %f \n", sav.getName(), alberoVisita.get(sav).getName(),
						tgr.getWeight(graph, sav, alberoVisita.get(sav)));
			}
		}

		Model model = null;
		try {
			model = new Model();
			model.resetMagazzinoForTest();
		} catch (LegoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String, Part> missingParts = model.retrieveMissingPartsInMagazzino(visitaAmpiezza);
		System.out.println("PARTI MANCANTI \n");
		for (String keyPart : missingParts.keySet()) {
			System.out.println(missingParts.get(keyPart));
		}
		
		
		
		int percentualeCompletamentoGap = 30;
		
		
		RicorsioneResolverForGap rr = new RicorsioneResolverForGap(visitaAmpiezza, missingParts);
		List<List<Set>> setsList = rr.risolvi(percentualeCompletamentoGap);
		System.out.println("SET CHE CONVIENE ACQUISTARE\n");
		System.out.println(setsList);

		
		

	}

	@Override
	protected List<Set> init(List<Theme> themes) throws LegoException {
		List<Set> sets = new ArrayList<Set>();
		
		Set setA = new Set("A", "set A", Year.now(), 13, "salva");
		Set setB = new Set("B", "set B", Year.now(), 13, "salva");
		Set setC = new Set("C", "set C", Year.now(), 23, "salva");
		Set setD = new Set("D", "set D", Year.now(), 31, "salva");
		Set setE = new Set("E", "set E", Year.now(), 4, "salva");
		Set setF = new Set("F", "set F", Year.now(), 20, "salva");
		// Set setG = new Set("G", "set G", Year.now(), 33, "salva");
		// Set setH = new Set("H", "set H", Year.now(), 70, "salva");

		sets.add(setA);
		sets.add(setB);
		sets.add(setC);
		sets.add(setD);
		sets.add(setE);
		sets.add(setF);
		// sets.add(setG);
		// sets.add(setH);

		Color blu = new Color(1, "blu", "rgb_blu", true);
		Part pA1 = new Part("p1", " p 1", "plastica", blu, 1);
		Part pA2 = new Part("p2", " p 2", "plastica", blu, 2);
		Part pA3 = new Part("p3", " p 3", "plastica", blu, 4);
		Part pA4 = new Part("p4", " p 4", "plastica", blu, 6);
		Map<String, Part> partsA = new HashMap<String, Part>();
		partsA.put(pA1.getKeyForSearch(), pA1);
		partsA.put(pA2.getKeyForSearch(), pA2);
		partsA.put(pA3.getKeyForSearch(), pA3);
		partsA.put(pA4.getKeyForSearch(), pA4);
		setA.setParts(partsA);

		/*
		 * Part pB1 = new Part("p1", " p 1", "plastica", blu, 2); Part pB3 = new
		 * Part("p3", " p 3", "plastica", blu, 3); Part pB4 = new Part("p4", " p 4",
		 * "plastica", blu, 1);
		 */
		Part pB1 = new Part("p1", " p 1", "plastica", blu, 1);
		Part pB2 = new Part("p2", " p 2", "plastica", blu, 2);
		Part pB3 = new Part("p3", " p 3", "plastica", blu, 4);
		Part pB4 = new Part("p4", " p 4", "plastica", blu, 6);

		Map<String, Part> partsB = new HashMap<String, Part>();
		partsB.put(pB1.getKeyForSearch(), pB1);
		partsB.put(pB2.getKeyForSearch(), pB2);
		partsB.put(pB3.getKeyForSearch(), pB3);
		partsB.put(pB4.getKeyForSearch(), pB4);
		setB.setParts(partsB);

		Part pC1 = new Part("p1", " p 1", "plastica", blu, 2);
		Part pC2 = new Part("p2", " p 2", "plastica", blu, 2);
		Part pC3 = new Part("p3", " p 3", "plastica", blu, 3);
		Part pC4 = new Part("p4", " p 4", "plastica", blu, 16);
		Map<String, Part> partsC = new HashMap<String, Part>();
		partsC.put(pC1.getKeyForSearch(), pC1);
		partsC.put(pC2.getKeyForSearch(), pC2);
		partsC.put(pC3.getKeyForSearch(), pC3);
		partsC.put(pC4.getKeyForSearch(), pC4);
		setC.setParts(partsC);

		Part pD2 = new Part("p2", " p 2", "plastica", blu, 10);
		Part pD3 = new Part("p3", " p 3", "plastica", blu, 21);
		Map<String, Part> partsD = new HashMap<String, Part>();
		partsD.put(pD2.getKeyForSearch(), pD2);
		partsD.put(pD3.getKeyForSearch(), pD3);
		setD.setParts(partsD);

		Part pE1 = new Part("p1", " p 1", "plastica", blu, 1);
		Part pE2 = new Part("p2", " p 2", "plastica", blu, 1);
		Part pE3 = new Part("p3", " p 3", "plastica", blu, 1);
		Part pE4 = new Part("p4", " p 4", "plastica", blu, 1);
		Map<String, Part> partsE = new HashMap<String, Part>();
		partsE.put(pE1.getKeyForSearch(), pE1);
		partsE.put(pE2.getKeyForSearch(), pE2);
		partsE.put(pE3.getKeyForSearch(), pE3);
		partsE.put(pE4.getKeyForSearch(), pE4);
		setE.setParts(partsE);

		Part pF2 = new Part("p2", " p 2", "plastica", blu, 20);
		Map<String, Part> partsF = new HashMap<String, Part>();
		partsF.put(pF2.getKeyForSearch(), pF2);
		setF.setParts(partsF);
		
		
		return sets;
	}

}
