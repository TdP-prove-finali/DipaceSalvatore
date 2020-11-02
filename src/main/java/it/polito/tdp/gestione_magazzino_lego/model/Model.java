package it.polito.tdp.gestione_magazzino_lego.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

import it.polito.tdp.gestione_magazzino_lego.db.ILegoDAO;
import it.polito.tdp.gestione_magazzino_lego.db.LegoDAO;
import it.polito.tdp.gestione_magazzino_lego.model.bean.Part;
import it.polito.tdp.gestione_magazzino_lego.model.bean.Set;
import it.polito.tdp.gestione_magazzino_lego.model.bean.Theme;
import it.polito.tdp.gestione_magazzino_lego.model.exception.LegoException;
import it.polito.tdp.gestione_magazzino_lego.model.graph.GraphHelper;
import it.polito.tdp.gestione_magazzino_lego.model.ricorsione.RicorsioneResolver;
import it.polito.tdp.gestione_magazzino_lego.model.ricorsione.RicorsioneResolverForGap;

public class Model {
	private Map<String, Part> magazzino;
	private Map<Long, Theme> themesMap;
	private Graph<Set, DefaultEdge> graph;

	private static final ILegoDAO dao = new LegoDAO();
	//private static final ILegoDAO dao = new MockLegoDAO();
	
	public Model() throws LegoException {
		if (this.magazzino == null) {
			// this.magazzino =
			// LoadMagazzinoFactory.loadMagazzino(LoadMagazzinoFactory.Mode.DB);
			this.magazzino = getDAO().loadMagazzino();
		}
	}

	/*
	 * public void resetMagazzinoForTest() throws LegoException {
	 * this.magazzino.clear(); this.magazzino =
	 * LoadMagazzinoFactory.loadMagazzino(LoadMagazzinoFactory.Mode.STATIC_FOR_TEST)
	 * ; }
	 */

	/**
	 * @param magazzino the magazzino to set
	 */
	protected void setMagazzino(Map<String, Part> magazzino) {
		this.magazzino = magazzino;
	}

	private ILegoDAO getDAO() {
		return dao;
	}

	public Map<Long, Theme> retrieveThemes() throws LegoException {
		if (themesMap == null) {
			themesMap = getDAO().retrieveThemes();
		}
		return themesMap;

	}

	public List<Set> listSetsByTheme(Theme t) throws LegoException {
		return getDAO().listSetsByTheme(t);
	}

	public Map<String, Part> listPartsBySet(Set s) throws LegoException {
		return getDAO().retrievePartsForSet(s);
	}

	public List<Set> listSetsByThemes(List<Theme> themes) throws LegoException {

		List<Set> sets = new ArrayList<Set>();
		for (Theme t : themes) {
			sets.addAll(listSetsByTheme(t));
		}

		for (Set s : sets) {
			s.setParts(listPartsBySet(s));
		}

		return sets;

	}

	public void updateMagazzino(Set s) throws LegoException {
		Map<String, Part> parts = getDAO().retrievePartsForSet(s);

		List<Part> existentParts = new ArrayList<Part>();
		List<Part> newParts = new ArrayList<Part>();
		for (String partKey : parts.keySet()) {
			if (magazzino.containsKey(partKey)) {
				Part magazzinoPart = magazzino.get(partKey);
				int quantityInMagazzino = magazzinoPart.getQuantity();
				quantityInMagazzino += parts.get(partKey).getQuantity();
				
				Part p = new Part(magazzinoPart.getCode(), magazzinoPart.getName(), magazzinoPart.getMaterial(),
						magazzinoPart.getColor(), quantityInMagazzino);
				existentParts.add(p);
			} else {
				magazzino.put(partKey, parts.get(partKey));
				newParts.add(parts.get(partKey));
			}
		}

		getDAO().insertNewPartsInMagazzino(newParts);
		getDAO().updateExistentPartsInMagazzino(existentParts);
	}

	public float getCopertura(Set set) throws LegoException {
		Map<String, Part> parts = getDAO().retrievePartsForSet(set);
		set.setParts(parts);
		Map<String, Part> magazzinoParts = getMagazzino();

		float partsPresentInMagazzino = 0;
		// IMPORTANTI IMPLEMENTAZIONI metodi EQUALS nei bean
		for (String partKey : parts.keySet()) {
			System.out.println("<getCopertura> p: " + parts.get(partKey));
			if (magazzinoParts.keySet().contains(partKey)) {

				float magazzinoQuantity = magazzinoParts.get(partKey).getQuantity();
				partsPresentInMagazzino += ((parts.get(partKey).getQuantity() <= magazzinoQuantity)
						? parts.get(partKey).getQuantity()
						: magazzinoQuantity);
			}
		}
		float copertura = (float) (partsPresentInMagazzino / (float) set.getPartsNumber());
		return copertura;
	}

	/**
	 * @return the magazzino
	 */
	public Map<String, Part> getMagazzino() {
		return magazzino;
	}

	public Map<String, Part> retrieveMissingPartsInMagazzino(List<Set> requiredSet) {

		Map<String, Part> missingParts = new HashMap<String, Part>();
		Map<String, Part> requiredParts = new HashMap<String, Part>();
		for (Set s : requiredSet) {
			for (String keyPart : s.getParts().keySet()) {
				if (!requiredParts.containsKey(keyPart)) {
					Part p = new Part(s.getParts().get(keyPart).getCode(), s.getParts().get(keyPart).getName(),
							s.getParts().get(keyPart).getMaterial(), s.getParts().get(keyPart).getColor(),
							s.getParts().get(keyPart).getQuantity());
					requiredParts.put(keyPart, p);
				} else {
					requiredParts.get(keyPart).incrementQuantity(s.getParts().get(keyPart).getQuantity());
				}

			}
		}

		for (String partKey : requiredParts.keySet()) {
			if (getMagazzino().containsKey(partKey)) {
				if (getMagazzino().get(partKey).getQuantity() < requiredParts.get(partKey).getQuantity()) {
					Part p = new Part(requiredParts.get(partKey).getCode(), requiredParts.get(partKey).getName(),
							requiredParts.get(partKey).getMaterial(), requiredParts.get(partKey).getColor(),
							requiredParts.get(partKey).getQuantity() - getMagazzino().get(partKey).getQuantity());
					missingParts.put(partKey, p);
				}
			} else {
				missingParts.put(partKey, requiredParts.get(partKey));
			}
		}

		return missingParts;

	}

	public List<List<Set>> risolviRicorsione(List<Theme> themes, int percentualeAccoppiamento) throws LegoException {
		List<Set> sets = listSetsByThemes(themes);
		RicorsioneResolver rr = new RicorsioneResolver(sets, getMagazzino());
		return rr.risolvi(percentualeAccoppiamento);
	}

	public List<List<Set>> risolviRicorsioneForGap(Map<String, Part> missingParts, List<Set> sets,
			double percentualeCompletamentoGap) throws LegoException {

		RicorsioneResolverForGap rr = new RicorsioneResolverForGap(sets, missingParts);
		List<List<Set>> setsList = rr.risolvi(percentualeCompletamentoGap);

		return setsList;

	}

	public void createGraph(List<Theme> themes, double couplingPercentage) throws LegoException {
		GraphHelper gr = new GraphHelper();
		this.graph = gr.creaGrafo(themes, couplingPercentage);

	}

	public List<Set> getLinkedNodes() {
		GraphHelper gr = new GraphHelper();
		return gr.getLinkedNodes(this.graph);
	}

	public List<Set> visitaAmpiezza(Set source) {
		GraphHelper gr = new GraphHelper();
		return gr.visitaAmpiezza(this.graph, source);
	}

	public void resetMagazzinoForTest() {
		try {
			this.magazzino = LoadMagazzinoFactory.loadMagazzino(LoadMagazzinoFactory.Mode.STATIC_FOR_TEST);
		} catch (LegoException e) {
			this.magazzino = Collections.emptyMap();
			e.printStackTrace();
		}

	}

}
