package it.polito.tdp.gestione_magazzino_lego.model.ricorsione;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import it.polito.tdp.gestione_magazzino_lego.model.bean.Part;
import it.polito.tdp.gestione_magazzino_lego.model.bean.Set;

public class RicorsioneHelper {

	private List<Set> sets;
	private Map<String, Part> magazzino;

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
	protected Map<String, Part> getMagazzino() {
		return magazzino;
	}

	/**
	 * @param magazzino the magazzino to set
	 */
	protected void setMagazzino(Map<String, Part> magazzino) {
		this.magazzino = magazzino;
	}

	protected boolean areSoluzioniEquivalenti(List<Set> parziale, List<List<Set>> best) {
		if (parziale == null || parziale.isEmpty()) {
			if (best == null || best.isEmpty()) {
				return true;
			} else {
				return false;
			}
		}

		if (best == null || best.isEmpty()) {
			if (parziale == null || parziale.isEmpty()) {
				return true;
			} else {
				return false;
			}
		}

		if (best.get(0).size() != parziale.size()) {
			return false;
		}

		for (List<Set> soluzione : best) {
			/*
			 * //to avoid messing the order of the lists we will use a copy
			 */
			List<Set> soluzioneTemp = new ArrayList<Set>(soluzione);
			List<Set> parzialeTemp = new ArrayList<Set>(parziale);

			Collections.sort(soluzioneTemp);
			Collections.sort(parzialeTemp);

			if (parzialeTemp.equals(soluzioneTemp)) {
				return true;
			}

		}

		return false;
	}

	protected void updateMagazzino(Set s, String operation) {
		Map<String, Part> parts = s.getParts();

		for (String keyPart : parts.keySet()) {
			if (getMagazzino().containsKey(keyPart)) {
				Part magazzinoPart = getMagazzino().get(keyPart);
				if ("ADD".equals(operation)) {
					magazzinoPart.decrementQuantity(parts.get(keyPart).getQuantity());
				} else {
					magazzinoPart.incrementQuantity(parts.get(keyPart).getQuantity());
				}

			}

		}
	}

	protected int getNumberOfPartsInMagazzino() {
		int numberOfParts = 0;
		for (String keyPart : getMagazzino().keySet()) {
			numberOfParts += getMagazzino().get(keyPart).getQuantity();
		}

//		System.out.println("<getNumberOfPartsInMagazzino> " + numberOfParts);
		return numberOfParts;
	}

}
