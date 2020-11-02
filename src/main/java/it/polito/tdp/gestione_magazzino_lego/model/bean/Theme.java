package it.polito.tdp.gestione_magazzino_lego.model.bean;

import java.io.Serializable;

public class Theme implements Serializable, Comparable<Theme> {
	private static final long serialVersionUID = -7403155402538189275L;
	private final long id;
	private final String name;
	private final Theme parent;

	/**
	 * @param id
	 * @param name
	 * @param parent
	 */
	public Theme(long id, String name, Theme parent) {
		this.id = id;
		this.name = name;
		this.parent = parent;
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
	 * @return the parent
	 */
	public Theme getParent() {
		return parent;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
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
		Theme other = (Theme) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (parent == null) {
			if (other.parent != null)
				return false;
		} else if (!parent.equals(other.parent))
			return false;
		return true;
	}

	public String toLog() {
		return "Theme [id=" + id + ", name=" + name + ", parent_id=" + getParent().getId() + ", parent_id="
				+ getParent().getName() + "]";
	}

	@Override
	public String toString() {
		return getParent().getName() + " - " + getName();
	}

	@Override
	public int compareTo(Theme o) {
		// TODO Auto-generated method stub
		return (getParent().getName() + getName()).compareTo(o.getParent().getName() + o.getName());
	}

}
