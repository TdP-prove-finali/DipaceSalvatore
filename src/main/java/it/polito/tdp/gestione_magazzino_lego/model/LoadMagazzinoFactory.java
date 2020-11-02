package it.polito.tdp.gestione_magazzino_lego.model;

import java.util.HashMap;
import java.util.Map;

import it.polito.tdp.gestione_magazzino_lego.db.LegoDAO;
import it.polito.tdp.gestione_magazzino_lego.model.bean.Color;
import it.polito.tdp.gestione_magazzino_lego.model.bean.Part;
import it.polito.tdp.gestione_magazzino_lego.model.exception.LegoException;

public class LoadMagazzinoFactory {

	public static Map<String,Part> loadMagazzino(Mode mode) throws LegoException {
		
		return mode.loadMagazzino();
		
	}
	
	
	public enum Mode{
		DB {
			@Override
			Map<String, Part> loadMagazzino() throws LegoException {
				return new LegoDAO().loadMagazzino();
			}
			
		},
		STATIC_FOR_TEST {
			@Override
			Map<String, Part> loadMagazzino() throws LegoException {
				// TODO Auto-generated method stub
				Map<String,Part> magazzino = new HashMap<String,Part>();
				Color blu = new Color(1, "blu", "rgb_blu", true);
				Part m1 = new Part("p1", " p 1", "plastica", blu, 10);
				Part m2 = new Part("p2", " p 2", "plastica", blu, 10);
				Part m3 = new Part("p3", " p 3", "plastica", blu, 10);
				Part m4 = new Part("p4", " p 4", "plastica", blu, 10);
				magazzino.put(m1.getKeyForSearch(),m1);
				magazzino.put(m2.getKeyForSearch(),m2);
				magazzino.put(m3.getKeyForSearch(),m3);
				magazzino.put(m4.getKeyForSearch(),m4);
				return magazzino;
			}
		};

		abstract Map<String,Part> loadMagazzino() throws LegoException;
	}
}
