package it.polito.tdp.gestione_magazzino_lego.model.exception;

public class LegoException extends Exception {
	private static final long serialVersionUID = 4089162134825820521L;

	public LegoException(String message){
		super(message);
	}
	
	public LegoException(String message, Exception e){
		super(message, e);
	}

}
