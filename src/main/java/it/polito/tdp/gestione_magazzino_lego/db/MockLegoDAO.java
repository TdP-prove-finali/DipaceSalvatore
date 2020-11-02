package it.polito.tdp.gestione_magazzino_lego.db;

import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.gestione_magazzino_lego.model.bean.Color;
import it.polito.tdp.gestione_magazzino_lego.model.bean.Part;
import it.polito.tdp.gestione_magazzino_lego.model.bean.Set;
import it.polito.tdp.gestione_magazzino_lego.model.bean.Theme;
import it.polito.tdp.gestione_magazzino_lego.model.exception.LegoException;

public class MockLegoDAO implements ILegoDAO{
	private static final MockLegoDAO instance = new MockLegoDAO();
	private final Map<Long,Theme> themes = new HashMap<Long,Theme>();
	private final List<Set> sets = new ArrayList<Set>();
	private final Map<String,Part> magazzino = new HashMap<String,Part>();
	
	public MockLegoDAO() {
		init();
	};
	
	public static MockLegoDAO getInstance() {
		return instance;
	}
	
	private void init() {
		Theme theme = new Theme(1, "DUMMY", null);
		Theme themeA = new Theme(2, "DUMMY_A", theme);
		Theme themeB = new Theme(3, "DUMMY_B", theme);
		themes.put(Long.valueOf(1),themeA);
		themes.put(Long.valueOf(2),themeB);
		
		Set setA = new Set("A", "set A", Year.now(), 13, "salva");
		Set setB = new Set("B", "set B", Year.now(), 13, "salva");
		Set setC = new Set("C", "set C", Year.now(), 51, "salva");
		Set setD = new Set("D", "set D", Year.now(), 31, "salva");
		Set setE = new Set("E", "set E", Year.now(), 4, "salva");
		Set setF = new Set("F", "set F", Year.now(), 20, "salva");
		Set setG = new Set("G", "set G", Year.now(), 33, "salva");
		Set setH = new Set("H", "set H", Year.now(), 70, "salva");
		
		sets.add(setA);	
		sets.add(setB);	
		sets.add(setC);	
		sets.add(setD);	
		sets.add(setE);	
		sets.add(setF);	
		sets.add(setG);	
		sets.add(setH);	
		
		
		Color blu = new Color(1, "blu", "rgb_blu", true);
		Part pA1 = new Part("p1", " p 1", "plastica", blu, 1);
		Part pA2 = new Part("p2", " p 2", "plastica", blu, 2);
		Part pA3 = new Part("p3", " p 3", "plastica", blu, 4);
		Part pA4 = new Part("p4", " p 4", "plastica", blu, 6);
		Map<String,Part> partsA = new HashMap<String,Part>();
		partsA.put(pA1.getKeyForSearch(), pA1);
		partsA.put(pA2.getKeyForSearch(), pA2);
		partsA.put(pA3.getKeyForSearch(), pA3);
		partsA.put(pA4.getKeyForSearch(), pA4);
		setA.setParts(partsA);
		
		/*
		Part pB1 = new Part("p1", " p 1", "plastica", blu, 2);
		Part pB3 = new Part("p3", " p 3", "plastica", blu, 3);
		Part pB4 = new Part("p4", " p 4", "plastica", blu, 1);
		*/
		Part pB1 = new Part("p1", " p 1", "plastica", blu, 1);
		Part pB2 = new Part("p2", " p 2", "plastica", blu, 2);
		Part pB3 = new Part("p3", " p 3", "plastica", blu, 4);
		Part pB4 = new Part("p4", " p 4", "plastica", blu, 6);
		
		Map<String,Part> partsB = new HashMap<String,Part>();
		partsB.put(pB1.getKeyForSearch(), pB1);
		partsB.put(pB2.getKeyForSearch(), pB2);
		partsB.put(pB3.getKeyForSearch(), pB3);
		partsB.put(pB4.getKeyForSearch(), pB4);
		setB.setParts(partsB);

		Part pC1 = new Part("p1", " p 1", "plastica", blu, 30);
		Part pC2 = new Part("p2", " p 2", "plastica", blu, 2);
		Part pC3 = new Part("p3", " p 3", "plastica", blu, 3);
		Part pC4 = new Part("p4", " p 4", "plastica", blu, 16);
		Map<String,Part> partsC = new HashMap<String,Part>();
		partsC.put(pC1.getKeyForSearch(), pC1);
		partsC.put(pC2.getKeyForSearch(), pC2);
		partsC.put(pC3.getKeyForSearch(), pC3);
		partsC.put(pC4.getKeyForSearch(), pC4);
		setC.setParts(partsC);
		
		Part pD2 = new Part("p2", " p 2", "plastica", blu, 10);
		Part pD3 = new Part("p3", " p 3", "plastica", blu, 21);
		Map<String,Part> partsD = new HashMap<String,Part>();
		partsD.put(pD2.getKeyForSearch(), pD2);
		partsD.put(pD3.getKeyForSearch(), pD3);
		setD.setParts(partsD);

		Part pE1 = new Part("p1", " p 1", "plastica", blu, 1);
		Part pE2 = new Part("p2", " p 2", "plastica", blu, 1);
		Part pE3 = new Part("p3", " p 3", "plastica", blu, 1);
		Part pE4 = new Part("p4", " p 4", "plastica", blu, 1);
		Map<String,Part> partsE = new HashMap<String,Part>();
		partsE.put(pE1.getKeyForSearch(), pE1);
		partsE.put(pE2.getKeyForSearch(), pE2);
		partsE.put(pE3.getKeyForSearch(), pE3);
		partsE.put(pE4.getKeyForSearch(), pE4);
		setE.setParts(partsE);

		Part pF2 = new Part("p2", " p 2", "plastica", blu, 20);
		Map<String,Part> partsF = new HashMap<String,Part>();
		partsF.put(pF2.getKeyForSearch(), pF2);
		setF.setParts(partsF);

		Part pG1 = new Part("p1", " p 1", "plastica", blu, 4);
		Part pG2 = new Part("p2", " p 2", "plastica", blu, 6);
		Part pG3 = new Part("p3", " p 3", "plastica", blu, 11);
		Part pG4 = new Part("p4", " p 4", "plastica", blu, 12);
		Map<String,Part> partsG = new HashMap<String,Part>();
		partsG.put(pG1.getKeyForSearch(), pG1);
		partsG.put(pG2.getKeyForSearch(), pG2);
		partsG.put(pG3.getKeyForSearch(), pG3);
		partsG.put(pG4.getKeyForSearch(), pG4);
		setG.setParts(partsG);

		Part pH1 = new Part("p1", " p 1", "plastica", blu, 40);
		Part pH4 = new Part("p4", " p 4", "plastica", blu, 30);
		Map<String,Part> partsH = new HashMap<String,Part>();
		partsH.put(pH1.getKeyForSearch(), pH1);
		partsH.put(pH4.getKeyForSearch(), pH4);
		setH.setParts(partsH);

		
		Part m1 = new Part("p1", " p 1", "plastica", blu, 6);
		Part m2 = new Part("p2", " p 2", "plastica", blu, 6);
		Part m3 = new Part("p3", " p 3", "plastica", blu, 6);
		Part m4 = new Part("p4", " p 4", "plastica", blu, 6);
		magazzino.put(m1.getKeyForSearch(),m1);
		magazzino.put(m2.getKeyForSearch(),m2);
		magazzino.put(m3.getKeyForSearch(),m3);
		magazzino.put(m4.getKeyForSearch(),m4);
		

	}
	
	
	@Override
	public List<Set> listSets() throws LegoException {
		// TODO Auto-generated method stub
		return this.sets;
	}

	@Override
	public Map<Long, Theme> retrieveThemes() throws LegoException {
		// TODO Auto-generated method stub
		return this.themes;
	}

	@Override
	public List<Set> listSetsByTheme(Theme t) throws LegoException {
		// TODO Auto-generated method stub
		return this.sets;
	}

	@Override
	public Map<String, Set> mapSets() throws LegoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Part> retrievePartsForSet(Set set) throws LegoException {
		// TODO Auto-generated method stub
		return set.getParts();
	}

	@Override
	public Map<String, Part> loadMagazzino() throws LegoException {
		// TODO Auto-generated method stub
		return this.magazzino;
	}

	@Override
	public void insertNewPartsInMagazzino(List<Part> parts) throws LegoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateExistentPartsInMagazzino(List<Part> parts) throws LegoException {
		// TODO Auto-generated method stub
		
	}

	
	

}
