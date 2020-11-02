package it.polito.tdp.gestione_magazzino_lego.model.bean;

import java.io.Serializable;
import java.time.Year;
import java.util.Map;

public class Set implements Serializable, Comparable<Set> {
	private static final long serialVersionUID = -6924233813542025201L;
	private final String code;
	private final String name;
	private final Year year;
	private final int partsNumber;
	private final String theme;
	
	private Map<String,Part> parts;

	/**
	 * @param code
	 * @param name
	 * @param year
	 * @param partsNumber
	 * @param theme
	 */
	public Set(String code, String name, Year year, int partsNumber, String theme) {
		this.code = code;
		this.name = name;
		this.year = year;
		this.partsNumber = partsNumber;
		this.theme = theme;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the year
	 */
	public Year getYear() {
		return year;
	}

	/**
	 * @return the partsNumber
	 */
	public int getPartsNumber() {
		int partsNumber = 0;
		//return partsNumber;
		if(getParts() == null) {
			partsNumber = 0;
		}
		else {
			for(Part p : getParts().values()) {
				partsNumber += p.getQuantity();
			}
			
		}
		
		return partsNumber;
		
	}

	/**
	 * @return the theme
	 */
	public String getTheme() {
		return theme;
	}


	/**
	 * @return the parts
	 */
	public Map<String, Part> getParts() {
		return parts;
	}

	/**
	 * @param parts the parts to set
	 */
	public void setParts(Map<String, Part> parts) {
		this.parts = parts;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + partsNumber;
		result = prime * result + ((theme == null) ? 0 : theme.hashCode());
		result = prime * result + ((year == null) ? 0 : year.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Set other = (Set) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;

		return true;
	}


	public String toLog() {
		return "Set [code=" + code + ", name=" + name + ", year=" + year + ", partsNumber=" + partsNumber + ", theme="
				+ theme + "]";
	}

	@Override
	public String toString() {
		return code + " - " + name;
	}

	@Override
	public int compareTo(Set o) {
		// TODO Auto-generated method stub
		return getCode().compareTo(o.getCode());
	}

	
	
}
