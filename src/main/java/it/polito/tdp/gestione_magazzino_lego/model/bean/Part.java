package it.polito.tdp.gestione_magazzino_lego.model.bean;

import java.io.Serializable;

public class Part implements Serializable {
	private static final long serialVersionUID = -5692001660101321906L;

	private final String code;
	private final String name;
	private final String material;
	private final Color color;
	private int quantity;

	/**
	 * @param code
	 * @param name
	 * @param material
	 * @param color
	 * @param quantity
	 */
	public Part(String code, String name, String material, Color color, int quantity) {
		super();
		this.code = code;
		this.name = name;
		this.material = material;
		this.color = color;
		this.quantity = quantity;
	}

	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
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
	 * @return the material
	 */
	public String getMaterial() {
		return material;
	}

	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	public String getKeyForSearch() {
		return new StringBuilder(getCode()).append(getColor().getId()).append(getMaterial()).toString();
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + ((material == null) ? 0 : material.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Part other = (Part) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		if (material == null) {
			if (other.material != null)
				return false;
		} else if (!material.equals(other.material))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Part [code=" + code + ", name=" + name + ", material=" + material + ", color=" + color + ", quantity="
				+ quantity + "]";
	}

	public void incrementQuantity(int quantity) {
		this.quantity += quantity;		
	}

	public void decrementQuantity(int quantity) {
		this.quantity -= quantity;
		
	}

	
	
}
