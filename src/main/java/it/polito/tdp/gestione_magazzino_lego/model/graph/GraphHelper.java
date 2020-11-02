package it.polito.tdp.gestione_magazzino_lego.model.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.event.ConnectedComponentTraversalEvent;
import org.jgrapht.event.EdgeTraversalEvent;
import org.jgrapht.event.TraversalListener;
import org.jgrapht.event.VertexTraversalEvent;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import it.polito.tdp.gestione_magazzino_lego.model.Model;
import it.polito.tdp.gestione_magazzino_lego.model.bean.Set;
import it.polito.tdp.gestione_magazzino_lego.model.bean.Theme;
import it.polito.tdp.gestione_magazzino_lego.model.exception.LegoException;

public class GraphHelper {

	public Graph<Set, DefaultEdge> creaGrafo(List<Theme> themes, double threshold) throws LegoException {

		Graph<Set, DefaultEdge> graph;

		List<Set> sets = init(themes);

		graph = new SimpleWeightedGraph<Set, DefaultEdge>(DefaultEdge.class);
		Graphs.addAllVertices(graph, sets);

		// aggiungo un arco tra tutti i set, con peso x = coefficiente di accoppiamento
		for (Set s1 : graph.vertexSet()) {
			for (Set s2 : graph.vertexSet()) {
				if (!s1.equals(s2)) {
					Float accoppiamento = calcolaAccoppiamento(s1, s2);
					if (accoppiamento * 100 >= threshold) {
						Graphs.addEdgeWithVertices(graph, s1, s2, accoppiamento);
					}
				}
			}
		}

		return graph;

	}

	protected List<Set> init(List<Theme> themes) throws LegoException {
		List<Set> sets = new Model().listSetsByThemes(themes);
		return sets;
	}

	public List<Set> visitaAmpiezza(Graph<Set, DefaultEdge> graph, Set source) {
		List<Set> visita = new ArrayList<Set>();
		BreadthFirstIterator<Set, DefaultEdge> bfi = new BreadthFirstIterator<Set, DefaultEdge>(graph, source);
		while (bfi.hasNext()) {
			visita.add(bfi.next());
		}

		return visita;
	}

	public List<Set> getLinkedNodes(Graph<Set, DefaultEdge> graph) {
		List<Set> sets = new ArrayList<Set>();

		if (graph == null) {
			return Collections.emptyList();
		} else {
			for (Set s : graph.vertexSet()) {
				if (graph.degreeOf(s) > 0) {
					sets.add(s);
				}
			}

		}
		return sets;
	}

	public Map<Set, Set> alberoVisita(Graph<Set, DefaultEdge> graph, Set source) {
		Map<Set, Set> albero = new HashMap<Set, Set>();
		albero.put(source, null);
		BreadthFirstIterator<Set, DefaultEdge> bfi = new BreadthFirstIterator<Set, DefaultEdge>(graph, source);
		bfi.addTraversalListener(new TraversalListener<Set, DefaultEdge>() {

			@Override
			public void vertexTraversed(VertexTraversalEvent<Set> arg0) {
			}

			@Override
			public void vertexFinished(VertexTraversalEvent<Set> arg0) {
			}

			@Override
			public void edgeTraversed(EdgeTraversalEvent<DefaultEdge> edge) {
				DefaultEdge de = edge.getEdge();
				Set source = graph.getEdgeSource(de);
				Set target = graph.getEdgeTarget(de);
				if (albero.containsKey(source)) {
					albero.put(target, source);
				} else {
					albero.put(source, target);
				}

			}

			@Override
			public void connectedComponentStarted(ConnectedComponentTraversalEvent arg0) {
			}

			@Override
			public void connectedComponentFinished(ConnectedComponentTraversalEvent arg0) {
			}
		});

		while (bfi.hasNext()) {
			bfi.next();
		}

		return albero;

	}

	private Float calcolaAccoppiamento(Set s1, Set s2) {

		float coefficienteAccoppiamento;
		// se uno dei due set non ha pezzi -->0
		if (s1.getParts().isEmpty() || s1.getParts().isEmpty()) {
			coefficienteAccoppiamento = 0;

		} else {
			int numeroPezziInComune = 0;
			if (s1.getPartsNumber() >= s2.getPartsNumber()) {
				for (String keyPart : s2.getParts().keySet()) {
					if (s1.getParts().containsKey(keyPart)) {
						if (s1.getParts().get(keyPart).getQuantity() >= s2.getParts().get(keyPart).getQuantity()) {
							numeroPezziInComune += s2.getParts().get(keyPart).getQuantity();
						} else {
							numeroPezziInComune += s1.getParts().get(keyPart).getQuantity();
						}
					}
				}

			} else {
				for (String keyPart : s1.getParts().keySet()) {
					if (s2.getParts().containsKey(keyPart)) {
						if (s2.getParts().get(keyPart).getQuantity() >= s1.getParts().get(keyPart).getQuantity()) {
							numeroPezziInComune += s1.getParts().get(keyPart).getQuantity();
						} else {
							numeroPezziInComune += s2.getParts().get(keyPart).getQuantity();
						}
					}
				}

			}
			coefficienteAccoppiamento = (float) (numeroPezziInComune * numeroPezziInComune)
					/ ((s1.getPartsNumber() * s2.getPartsNumber()));
		}

		return coefficienteAccoppiamento;
	}

	protected float getWeight(Graph<Set, DefaultEdge> graph, Set source, Set destination) {

		return (float) graph.getEdgeWeight(graph.getEdge(source, destination));

	}

	public Map<Set, List<Set>> retrieveNeighbors(Graph<Set, DefaultEdge> graph) {
		Map<Set, List<Set>> neighbors = new HashMap<Set, List<Set>>();
		for (Set s : graph.vertexSet()) {
			neighbors.put(s, Graphs.neighborListOf(graph, s));

		}
		return neighbors;
	}

}
