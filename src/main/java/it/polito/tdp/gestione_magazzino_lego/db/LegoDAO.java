package it.polito.tdp.gestione_magazzino_lego.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

public class LegoDAO implements ILegoDAO{

	public List<Set> listSets() throws LegoException {
		String sql = "SELECT s.set_num, t.name AS theme_name, s.name AS set_name, s.year, s.num_parts " + 
					 "FROM sets s, themes t " + 
				     "WHERE s.theme_id = t.id " +
					 "AND s.num_parts > 0";
		Connection c = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		List<Set> setsList = new ArrayList<>();

		try {
			c = DBConnect.getConnection();
			st = c.prepareStatement(sql);

			rs = st.executeQuery();

			while (rs.next()) {
				Set set = new Set(rs.getString("set_num"), rs.getString("set_name"), Year.of(rs.getInt("year")),
						rs.getInt("num_parts"), rs.getString("theme_name"));
				// System.out.println("<listSets> " + set);
				setsList.add(set);
			}
			System.out.println("<listSets> list size: " + setsList.size());

			return setsList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new LegoException("Errore nel recupero dei set.", e);
		} finally {
			DBConnect.closeResources(c, st, rs);
		}

	}

	public Map<Long, Theme> retrieveThemes() throws LegoException {
		String sql = "SELECT sons.id AS son_id, sons.name AS son_name, parents.id AS parent_id, parents.name AS parent_name " + 
					 "FROM themes sons, themes parents " + 
					 "WHERE sons.parent_id = parents.id " + 
					 "ORDER BY parents.name, sons.name";
		Connection c = null;
		PreparedStatement st = null;
		ResultSet rs = null;

		Map<Long, Theme> themesMap = new HashMap<Long,Theme>();

		try {
			c = DBConnect.getConnection();
			st = c.prepareStatement(sql);

			rs = st.executeQuery();

			while (rs.next()) {
				Theme t = new Theme(rs.getLong("son_id"), rs.getString("son_name"), new Theme(rs.getLong("parent_id"), rs.getString("parent_name"), null));
				themesMap.put(t.getId(),t);
			}
			System.out.println("<retrieveThemes> list size: " + themesMap.size());

			return themesMap;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new LegoException("Errore nel recupero dei temi.", e);
		} finally {
			DBConnect.closeResources(c, st, rs);
		}

	}

	
	public List<Set> listSetsByTheme(Theme t) throws LegoException {
		String sql = "SELECT s.set_num, t.name AS theme_name, s.name AS set_name, " +
					 "s.year, s.num_parts " +
					 "FROM sets s, themes t " + 
					 "WHERE s.theme_id = t.id " + 
					 "AND t.id = ? " +
					 "AND s.num_parts > 0";
		Connection c = null;
		PreparedStatement st = null;
		ResultSet rs = null;

		List<Set> setsList = new ArrayList<>();

		try {
			c = DBConnect.getConnection();
			st = c.prepareStatement(sql);
			st.setLong(1, t.getId());

			rs = st.executeQuery();

			while (rs.next()) {
				Set set = new Set(rs.getString("set_num"), rs.getString("set_name"), Year.of(rs.getInt("year")),
						rs.getInt("num_parts"), rs.getString("theme_name"));
				// System.out.println("<listSets> " + set);
				setsList.add(set);
			}
			System.out.println("<listSetsByTheme> list size: " + setsList.size());

			if(setsList.size()==0) {
				throw new LegoException("Non ci sono set collegati alla serie: " + t);
			}
			
			return setsList;

		} catch (LegoException le) {
			throw le;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new LegoException("Errore nel recupero dei set.", e);
		} finally {
			DBConnect.closeResources(c, st, rs);
		}

	}

	public Map<String, Set> mapSets() throws LegoException {
		String sql = "SELECT s.set_num, t.name AS theme_name, s.name AS set_name, s.year, s.num_parts "
				+ "FROM sets s, themes t " + "WHERE s.theme_id = t.id";
		Connection c = null;
		PreparedStatement st = null;
		ResultSet rs = null;

		Map<String, Set> setsMap = new HashMap<String, Set>();

		try {
			c = DBConnect.getConnection();
			st = c.prepareStatement(sql);

			rs = st.executeQuery();

			while (rs.next()) {
				Set set = new Set(rs.getString("set_num"), rs.getString("set_name"), Year.of(rs.getInt("year")),
						rs.getInt("num_parts"), rs.getString("theme_name"));
				// System.out.println("<listSets> " + set);
				setsMap.put(rs.getString("set_num"), set);
			}
			System.out.println("<listSets> list size: " + setsMap.keySet().size());

			return setsMap;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new LegoException("Errore nel recupero dei set.", e);
		} finally {
			DBConnect.closeResources(c, st, rs);
		}

	}

	public Map<String,Part> retrievePartsForSet(Set set) throws LegoException {
		String sql = "SELECT p.part_num AS part_code, p.name, ip.quantity, p.part_material, " + 
							"c.id AS color_id, c.name AS color, c.rgb, c.is_trans " + 
					 "FROM inventory_parts ip, parts p, colors c " + 
					 "WHERE ip.part_num = p.part_num " + 
					 "AND ip.color_id = c.id " + 
					 "AND inventory_id IN ( " + 
					 	"SELECT i.id " + 
					 	"FROM inventories i, sets s " + 
					 	"WHERE i.set_num = s.set_num " + 
					 	"AND s.set_num = ? " + 
					 ") " + 
				"AND is_spare = 'f'";
		Connection c = null;
		PreparedStatement st = null;
		ResultSet rs = null;

		Map<String,Part> partsMap = new HashMap<>();

		try {
			c = DBConnect.getConnection();
			st = c.prepareStatement(sql);
			st.setString(1, set.getCode());

			rs = st.executeQuery();

			while (rs.next()) {
				Part part = new Part(rs.getString("part_code"), rs.getString("name"), rs.getString("part_material"),
						new Color(rs.getLong("color_id"), rs.getString("color"), rs.getString("rgb"), "t".equals(rs.getString("is_trans"))), rs.getInt("quantity"));

				partsMap.put(part.getKeyForSearch(),part);
			}
			System.out.println("<retrievePartsForSet> map size: " + partsMap.size());

			return partsMap;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new LegoException("Errore nel recupero delle parti di un set.", e);
		} finally {
			DBConnect.closeResources(c, st, rs);
		}

	}

	public Map<String,Part> loadMagazzino() throws LegoException {
		String sql = "SELECT partial_magazzino.part_num AS part_code, p.name, partial_magazzino.total_quantity AS quantity, " + 
							"p.part_material, c.id AS color_id, c.name AS color, c.rgb, c.is_trans " + 
					 "FROM " + 
					 "( " + 
						"SELECT part_num, color_id, SUM(quantity) AS total_quantity " + 
						"FROM magazzino " + 
						"GROUP BY part_num, color_id) partial_magazzino, " + 
					 "parts p, colors c " + 
					 "WHERE partial_magazzino.part_num = p.part_num " + 
					 "AND partial_magazzino.color_id = c.id";
		Connection c = null;
		PreparedStatement st = null;
		ResultSet rs = null;

		Map<String,Part> magazzino = new HashMap<String,Part>();

		try {
			c = DBConnect.getConnection();
			st = c.prepareStatement(sql);

			rs = st.executeQuery();

			while (rs.next()) {
				Part part = new Part(rs.getString("part_code"), rs.getString("name"), rs.getString("part_material"),
						new Color(rs.getLong("color_id"), rs.getString("color"), rs.getString("rgb"), "t".equals(rs.getString("is_trans"))), rs.getInt("quantity"));

				magazzino.put(part.getCode()+part.getColor().getId()+part.getMaterial(), part);
			}
			System.out.println("<loadMagazzino> magazzino size: " + magazzino.size());

			return magazzino;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new LegoException("Errore nel recupero del magazzino.", e);
		} finally {
			DBConnect.closeResources(c, st, rs);
		}
	}


	public void insertNewPartsInMagazzino(List<Part> parts) throws LegoException {
		System.out.println("<insertNewPartsInMagazzino>");
		
		String sql = "INSERT INTO magazzino (quantity, part_num, color_id) " + 
					 "VALUES (?,?,?)";
			
		updateMagazzino(parts, sql);
	
	}

	public void updateExistentPartsInMagazzino(List<Part> parts) throws LegoException {
		System.out.println("<updateExistentPartsInMagazzino>");
		
		String sql = "UPDATE magazzino " + 
					 "SET quantity = quantity + ? " + 
					 "WHERE part_num = ? " + 
					 "AND color_id = ?";
		
		updateMagazzino(parts, sql);
	}

	private void updateMagazzino(List<Part> parts, String statement) throws LegoException {
		System.out.println("<updateMagazzino>");
		
		Connection c = null;
		PreparedStatement st = null;
		try {
			c = DBConnect.getConnection();
			st = c.prepareStatement(statement);
			
			for(Part p : parts) {
				st.setInt(1, p.getQuantity());
				st.setString(2, p.getCode());
				st.setLong(3, p.getColor().getId());
				
				st.addBatch();
			}
			st.executeBatch();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new LegoException("Errore nell'aggiornamento del magazzino.", e);
		} finally {
			DBConnect.closeResources(c, st, null);
		}
		
	}

}
