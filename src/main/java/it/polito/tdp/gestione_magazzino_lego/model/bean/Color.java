package it.polito.tdp.gestione_magazzino_lego.model.bean;

import java.io.Serializable;

public class Color implements Serializable{
	private static final long serialVersionUID = 3452928988578376380L;
	private final long id;
	private final String name;
	private final String rgb;
	private final boolean isTrans;

	/**
	 * @param id
	 * @param name
	 * @param rgb
	 * @param isTrans
	 */
	public Color(long id, String name, String rgb, boolean isTrans) {
		super();
		this.id = id;
		this.name = name;
		this.rgb = rgb;
		this.isTrans = isTrans;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the rgb
	 */
	public String getRgb() {
		return rgb;
	}

	/**
	 * @return the isTrans
	 */
	public boolean isTrans() {
		return isTrans;
	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Color other = (Color) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Color [id=" + id + ", name=" + name + ", rgb=" + rgb + ", isTrans=" + isTrans + "]";
	}

}
