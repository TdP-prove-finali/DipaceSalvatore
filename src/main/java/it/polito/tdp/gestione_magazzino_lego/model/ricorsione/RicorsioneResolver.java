package it.polito.tdp.gestione_magazzino_lego.model.ricorsione;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.gestione_magazzino_lego.model.bean.Part;
import it.polito.tdp.gestione_magazzino_lego.model.bean.Set;

public class RicorsioneResolver extends RicorsioneHelper {

	public RicorsioneResolver(List<Set> sets, Map<String, Part> magazzino) {
		init(sets, magazzino);

	}

	protected void init(List<Set> sets, Map<String, Part> magazzino) {
		setSets(sets);
		setMagazzino(magazzino);

	}

	public List<List<Set>> risolvi(int percentualeCompletamento) {
		// TODO Auto-generated method stub

		java.util.List<Set> parziale = new ArrayList<Set>();
		List<java.util.List<Set>> best = new ArrayList<List<Set>>();

		scegli(parziale, best, percentualeCompletamento);
		System.out.println("<risolvi> best " + best);

		return best;
	}

	protected void scegli(List<Set> parziale, List<List<Set>> best, int percentualeCompletamento) {

		if (!parziale.isEmpty() && parziale
				.size() >= ((best == null || best.isEmpty() || best.get(0) == null) ? 0 : best.get(0).size())) {
			// trovato soluzione migliore

			if (parziale.size() > ((best == null || best.isEmpty() || best.get(0) == null) ? 0 : best.get(0).size())) {
				best.clear();
			}

			if (!areSoluzioniEquivalenti(parziale, best)) {
				java.util.List<Set> temp = new ArrayList<Set>();
				temp.addAll(parziale);
				best.add(temp);
			}

		}

		for (Set s : getSets()) {
			if (!parziale.contains(s)) {
				// il set non c'è ancora in parziale e provo ad aggiungerlo
				if (areThePartsToBuildTheSetWithTheRequiredPercentageInStock(s, percentualeCompletamento)) {
					// nuova soluzione parziale
					parziale.add(s);
					updateMagazzino(s, "ADD");
					// si delega la ricerca al livello successivo
					scegli(parziale, best, percentualeCompletamento);
					// backtracking
					parziale.remove(s);
					updateMagazzino(s, "REMOVE");
				}

			}

		}

	}

	protected boolean areThePartsToBuildTheSetWithTheRequiredPercentageInStock(Set s, int requiredPercentage) {
		Map<String, Part> parts = s.getParts();
		Map<String, Part> magazzinoTemp = new HashMap<String, Part>();
		magazzinoTemp.putAll(getMagazzino());

		int availablePartsNumber = 0;
		for (String keyPart : parts.keySet()) {

			if (magazzinoTemp.containsKey(keyPart)) {
				Part magazzinoPart = magazzinoTemp.get(keyPart);

				if (magazzinoPart.getQuantity() < parts.get(keyPart).getQuantity()) {
					availablePartsNumber += magazzinoPart.getQuantity();

				} else {
					availablePartsNumber += parts.get(keyPart).getQuantity();
				}

			}

		}
		int percentage = (availablePartsNumber * 100) / s.getPartsNumber();
		return percentage >= requiredPercentage;

	}

}
