package it.polito.tdp.gestione_magazzino_lego.model.ricorsione;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.gestione_magazzino_lego.model.bean.Part;
import it.polito.tdp.gestione_magazzino_lego.model.bean.Set;

public class RicorsioneResolverForGap extends RicorsioneHelper {

	private List<Set> sets;
	private Map<String, Part> missingParts;

	/**
	 * @return the sets
	 */
	protected List<Set> getSets() {
		return sets;
	}

	/**
	 * @param sets the sets to set
	 */
	protected void setSets(List<Set> sets) {
		this.sets = sets;
	}

	/**
	 * @return the magazzino
	 */
	protected Map<String, Part> getMissingParts() {
		return missingParts;
	}

	/**
	 * @param missingParts the missingParts to set
	 */
	protected void setMissingParts(Map<String, Part> missingParts) {
		this.missingParts = missingParts;
	}

	public RicorsioneResolverForGap(List<Set> sets, Map<String, Part> missingParts) {
		init(sets, missingParts);
		// TODO Auto-generated constructor stub
	}

	private void init(List<Set> sets, Map<String, Part> missingParts) {
		setSets(sets);
		setMissingParts(missingParts);

	}

	public List<List<Set>> risolvi(double percentualeCompletamento) {

		java.util.List<Set> parziale = new ArrayList<Set>();
		parziale.addAll(getSets());

		List<java.util.List<Set>> best = new ArrayList<List<Set>>();

		int gapPartsSize = getNumberOfMissingParts();

		
		
		scegli(parziale, best, percentualeCompletamento, gapPartsSize);
		System.out.println("<risolvi> best " + best);

		return best;
	}

	private int getNumberOfMissingParts() {
		int numberOfParts = 0;
		for (Part p : getMissingParts().values()) {
			numberOfParts += p.getQuantity();
		}

		return numberOfParts;
	}

	protected void scegli(List<Set> parziale, List<List<Set>> best, double percentualeCompletamento, int gapPartsSize) {

		if (!parziale.equals(getSets())
				&& parziale.size() <= ((best == null || best.isEmpty() || best.get(0) == null) ? getSets().size()
						: best.get(0).size())) {

			// trovato soluzione migliore

			if (parziale.size() < ((best == null || best.isEmpty() || best.get(0) == null) ? getSets().size()
					: best.get(0).size())) {
				best.clear();
			}

			if (!areSoluzioniEquivalenti(parziale, best)) {
				java.util.List<Set> temp = new ArrayList<Set>();
				temp.addAll(parziale);
				best.add(temp);
			}

		}

		for (Set s : getSets()) {
			if (parziale.contains(s)) {
				if (areTheSetsAbleToCoverTheRequiredPercentageofTheGap(s, parziale, percentualeCompletamento,
						gapPartsSize)) {
					// nuova soluzione parziale
					parziale.remove(s);
					scegli(parziale, best, percentualeCompletamento, gapPartsSize);
					// backtracking
					parziale.add(s);
				}

			}

		}

	}

	private boolean areTheSetsAbleToCoverTheRequiredPercentageofTheGap(Set s, List<Set> parziale,
			double percentualeCompletamento, int gapPartsSize) {
		Map<String, Part> parts = new HashMap<String, Part>();
		for (Set parzialeSet : parziale) {
			if (!s.equals(parzialeSet)) {
				for (String partKey : parzialeSet.getParts().keySet()) {
					if (!parts.containsKey(partKey)) {
						Part parzialeSetPart = parzialeSet.getParts().get(partKey);
						Part p = new Part(parzialeSetPart.getCode(), parzialeSetPart.getName(),
								parzialeSetPart.getMaterial(), parzialeSetPart.getColor(),
								parzialeSetPart.getQuantity());
						parts.put(partKey, p);
					} else {
						parts.get(partKey).incrementQuantity(parzialeSet.getParts().get(partKey).getQuantity());
					}
				}

			}
		}

		Map<String, Part> gapTemp = new HashMap<String, Part>();
		gapTemp.putAll(getMissingParts());

		int partsForGapNumber = 0;
		for (String partKey : parts.keySet()) {
			if (gapTemp.containsKey(partKey)) {
				if (parts.get(partKey).getQuantity() >= gapTemp.get(partKey).getQuantity()) {
					partsForGapNumber += gapTemp.get(partKey).getQuantity();
				} else {
					partsForGapNumber += parts.get(partKey).getQuantity();
				}
			}
		}

		int percentage = (partsForGapNumber * 100) / gapPartsSize;

		return percentage >= percentualeCompletamento;

	}

}
