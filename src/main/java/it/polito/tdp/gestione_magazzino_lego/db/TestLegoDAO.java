package it.polito.tdp.gestione_magazzino_lego.db;

import java.time.Year;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import it.polito.tdp.gestione_magazzino_lego.model.bean.Part;
import it.polito.tdp.gestione_magazzino_lego.model.bean.Set;
import it.polito.tdp.gestione_magazzino_lego.model.exception.LegoException;

public class TestLegoDAO {

	public static void main(String[] args) {

		LegoDAO dao = new LegoDAO();
		

		List<Set> sets;
		try {
			sets = dao.listSets();
			System.out.println("<main> sets list size: " + sets.size());
		} catch (LegoException e) {
			e.printStackTrace();
			sets = Collections.emptyList();
		}

		Set camperT1WTemp = new Set("10220-1", "", Year.now(), 0, null);

		Set camperT1W = sets.get(sets.indexOf(camperT1WTemp));
		System.out.println("<main> T1 camper W: " + camperT1W);

		Map<String, Set> setsMap;
		try {
			setsMap = dao.mapSets();
			System.out.println("<main> sets map size: " + setsMap.keySet().size());
		} catch (LegoException e) {
			e.printStackTrace();
			setsMap = Collections.emptyMap();
		}

		camperT1W = setsMap.get("10220-1");
		System.out.println("<main> T1 camper W: " + camperT1W);

		Map<String,Part> parts;
		try {
			parts = dao.retrievePartsForSet(camperT1W);
			System.out.println("<main> parts: " + parts.size());
		} catch (LegoException e) {
			e.printStackTrace();
			parts = Collections.emptyMap();
		}

		int totalNumberOfPieces = 0;
		for (Part part : parts.values()) {
			totalNumberOfPieces += part.getQuantity();
		}
		System.out.println("<main> totalNumberOfPieces: " + totalNumberOfPieces);
		
		
		//scelto set e comprato, aggiorno magazzino pezzi disponibili
		try {
			dao.insertNewPartsInMagazzino(new ArrayList<Part>(parts.values()));
			System.out.println("<main> magazzino aggiornato");
		} catch (LegoException e) {
			e.printStackTrace();
		}

		try {
			dao.retrieveThemes();
		} catch (LegoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	

}
