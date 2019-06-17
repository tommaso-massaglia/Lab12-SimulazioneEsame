package it.polito.tdp.model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.db.EventsDao;

public class Simulatore {

	private List<Event> avvenimenti;
	private Graph<District, DefaultWeightedEdge> grafo;
	private int distretto;
	private Map<Integer, District> districtidmap;

	private int N; // numero di agenti disponibili

	private int malgestiti; // numero di eventi mal gestiti dopo la simulazione

	private PriorityQueue<Evento> queue; // coda degli eventi

	public void init(Graph<District, DefaultWeightedEdge> grafo, int N, LocalDateTime data,
			Map<Integer, District> districtidmap) {
		this.grafo = grafo;
		this.N = N;
		EventsDao dao = new EventsDao();
		this.avvenimenti = dao.listEventsByDate(data);
		this.malgestiti = 0;
		this.distretto = dao.leastEvents(data);
		this.queue = new PriorityQueue<Evento>();
		this.districtidmap = districtidmap;

		// EVENTI INIZIALI
		for (Event e : avvenimenti) {
			queue.add(new Evento(Evento.TIPO.INVIO, e.getReported_date(), e.getOffense_category_id(), 0,
					this.districtidmap.get(e.getDistrict_id())));
		}
	}

	// Probabilmente mi serve una shortestpath
	public void run() {
		Map<Integer, Agente> agenti = new HashMap<Integer, Agente>();
		for (int i = 1; i <= N; i++) {
			agenti.put(i, new Agente(i, true, this.districtidmap.get(distretto)));
		}
		Evento e;
		// CON EVENTI INVIO MANDO L'AGENTE CONTROLLANDO SE ARRIVA PER TEMPO, NELL'AGENTE
		// SALVO DISTRETTO
		// IN CUI VIENE MANDATO E CHE è OCCUPATO
		// CON EVENTI LIBERO LIBERO L'AGENTE CORRISPONDENTE E LO AGGIUNGO DI NUOVO AL
		// POOL DI AGENTI DISPONIBILI
		while ((e = queue.poll()) != null) {
			System.out.println("Inizio a gestire l'evento" + e);
			switch (e.getTipo()) {
			case INVIO:
				int id_agente_vicino = -1;
				double distanza = 0;
				double min_distanza = Double.MAX_VALUE;
				for (Agente a : agenti.values()) {
					if (a.getDistretto_attuale().getDistrict_id() == e.getDistretto().getDistrict_id())
						distanza = 0;
					else {
						this.grafo.getEdgeWeight(grafo.getEdge(a.getDistretto_attuale(), e.getDistretto()));
					}
					if (distanza < min_distanza && a.isLibero()) {
						id_agente_vicino = a.getId_agente();
						min_distanza = distanza;
					}
				}
				if (id_agente_vicino != -1) {
					double tempo = 0;
					if (agenti.get(id_agente_vicino).getDistretto_attuale().getDistrict_id() != e.getDistretto().getDistrict_id())
					tempo = grafo.getEdgeWeight(grafo.getEdge
							(agenti.get(id_agente_vicino).getDistretto_attuale(), e.getDistretto())) / 60/ 3.6;
					if (tempo < 15) {
						this.malgestiti++;
						System.out.println("EVENTO GESTITO MALE");
					}
					agenti.get(id_agente_vicino).setLibero(false);
					agenti.get(id_agente_vicino).setDistretto_attuale(e.getDistretto());
					System.out.println(agenti.get(id_agente_vicino));
					queue.add(new Evento(Evento.TIPO.LIBERO, e.getOrafine(), "", id_agente_vicino, null));
				} else {
					this.malgestiti++;
					System.out.println("EVENTO GESTITO MALE");
				}

				break;
			case LIBERO:
				agenti.get(e.getId_agente()).setLibero(true);
				System.out.println(agenti.get(e.getId_agente()));
				break;
			}
		}
	}

	public int getMalgestiti() {
		return this.malgestiti;
	}

}
