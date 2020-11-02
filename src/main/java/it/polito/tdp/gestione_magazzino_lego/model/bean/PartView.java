package it.polito.tdp.gestione_magazzino_lego.model.bean;

import javafx.beans.property.SimpleStringProperty;

public class PartView {
	private SimpleStringProperty code, material, color;
	private Integer quantity;
	/**
	 * @param code
	 * @param material
	 * @param color
	 * @param quantity
	 */
	public PartView(String code, String material, String color,
			Integer quantity) {
		this.code = new SimpleStringProperty(code);
		this.material = new SimpleStringProperty(material);
		this.color = new SimpleStringProperty(color);
		this.quantity = quantity;
	}
	/**
	 * @return the code
	 */
	public String getCode() {
		return code.get();
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(SimpleStringProperty code) {
		this.code = code;
	}
	/**
	 * @return the material
	 */
	public String getMaterial() {
		return material.get();
	}
	/**
	 * @param material the material to set
	 */
	public void setMaterial(SimpleStringProperty material) {
		this.material = material;
	}
	/**
	 * @return the color
	 */
	public String getColor() {
		return color.get();
	}
	/**
	 * @param color the color to set
	 */
	public void setColor(SimpleStringProperty color) {
		this.color = color;
	}
	/**
	 * @return the quantity
	 */
	public Integer getQuantity() {
		return quantity;
	}
	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	
	
	
	
}
