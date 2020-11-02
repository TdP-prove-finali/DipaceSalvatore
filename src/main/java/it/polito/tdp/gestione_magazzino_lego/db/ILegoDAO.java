package it.polito.tdp.gestione_magazzino_lego.db;

import java.util.List;
import java.util.Map;

import it.polito.tdp.gestione_magazzino_lego.model.bean.Part;
import it.polito.tdp.gestione_magazzino_lego.model.bean.Set;
import it.polito.tdp.gestione_magazzino_lego.model.bean.Theme;
import it.polito.tdp.gestione_magazzino_lego.model.exception.LegoException;

public interface ILegoDAO {
	
	public List<Set> listSets() throws LegoException;
	
	public Map<Long, Theme> retrieveThemes() throws LegoException;
	
	public List<Set> listSetsByTheme(Theme t) throws LegoException;

	public Map<String, Set> mapSets() throws LegoException ;
	
	public Map<String,Part> retrievePartsForSet(Set set) throws LegoException;
	
	public Map<String,Part> loadMagazzino() throws LegoException;
	
	public void insertNewPartsInMagazzino(List<Part> parts) throws LegoException;
	
	public void updateExistentPartsInMagazzino(List<Part> parts) throws LegoException;
	

}
