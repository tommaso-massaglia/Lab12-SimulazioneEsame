package it.polito.tdp.model;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultUndirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.db.EventsDao;

public class Model {

	private Graph<District, DefaultWeightedEdge> grafo;
	private List<District> distretti;
	private List<EventLatLong> eventi;
	private Map<Integer, District> districtidmap;

	public void creaGrafoDistretti(int anno) {

		this.grafo = new DefaultUndirectedWeightedGraph<>(DefaultWeightedEdge.class);
		EventsDao dao = new EventsDao();
		this.distretti = dao.listDistricts();
		this.districtidmap = new HashMap<>();
		for (District d : distretti) {
			districtidmap.put(d.getDistrict_id(), d);
		}
		this.eventi = dao.listEventsByYear(anno);

		double sommalon = 0;
		double sommalat = 0;
		int analizzati = 0;
		for (District d : districtidmap.values()) {
			for (EventLatLong e : eventi) {
				if (e.getDistrict_id() == d.getDistrict_id()) {
					sommalon += e.getGeo_lon();
					sommalat += e.getGeo_lat();
					analizzati++;
				}
			}
			this.districtidmap.get(d.getDistrict_id()).setGeo_lat(sommalat / analizzati);
			this.districtidmap.get(d.getDistrict_id()).setGeo_lon(sommalon / analizzati);
			this.districtidmap.get(d.getDistrict_id()).setCoords();
			sommalon = sommalat = analizzati = 0;
		}

		Graphs.addAllVertices(this.grafo, this.districtidmap.values());

		for (District d1 : districtidmap.values()) {
			for (District d2 : districtidmap.values()) {
				if (!d1.equals(d2) && !grafo.containsEdge(d1, d2)) {
					this.grafo.addEdge(d1, d2);
					double distanza = LatLngTool.distance(d1.getCoords(), d2.getCoords(), LengthUnit.KILOMETER);
					double peso = distanza;
					grafo.setEdgeWeight(grafo.getEdge(d1, d2), peso);
				}
			}
		}

	}

	public String getElencoDistretti() {
		String result = "";
		for (District d1 : grafo.vertexSet()) {
			class districtsorter implements Comparable<districtsorter> {
				District district;
				double weight;

				districtsorter(District district, double weight) {
					this.district = district;
					this.weight = weight;
				}

				@Override
				public int compareTo(districtsorter arg0) {
					return (int) (this.weight - arg0.weight);
				}
			}
			List<districtsorter> sortable = new LinkedList<districtsorter>();
			for (District d2 : Graphs.neighborListOf(grafo, d1)) {
				sortable.add(new districtsorter(d2, grafo.getEdgeWeight(grafo.getEdge(d1, d2))));
			}
			Collections.sort(sortable);
			result += "Elenco vicini per "+d1+":\n";
			for (districtsorter s : sortable) {
				result += s.district +" "+s.weight+"\n";
			}
		}
		return result;
	}

	public void simula (int N, int giorno, int mese, int anno) {
		Simulatore sim = new Simulatore();
		sim.init(grafo, N, LocalDateTime.of(anno, mese, giorno, 0, 0, 0),districtidmap);
		sim.run();
		System.out.println("Numero eventi getiti male: "+sim.getMalgestiti());
	} 	
	
}
