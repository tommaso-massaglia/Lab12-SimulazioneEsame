package it.polito.tdp.model;

import java.time.LocalDateTime;

public class Evento implements Comparable<Evento> {

	public enum TIPO {
		INVIO, LIBERO
	}

	private TIPO tipo;
	private LocalDateTime orainizio;
	private int durataevento;
	private LocalDateTime orafine;
	private int id_agente;
	private District distretto;

	public Evento(TIPO tipo, LocalDateTime ora, String tipoevento, int id_agente, District distretto) {
		this.tipo = tipo;
		this.orainizio = ora;
		if (tipoevento.equals("all-other-crimes")) {
			double rand = Math.random();
			if (rand > 0.5) {
				this.durataevento = 2;
			} else {
				this.durataevento = 1;
			}
		} else {
			this.durataevento = 2;
		}
		this.orafine = ora.plusHours(durataevento);
		this.id_agente = id_agente;
		this.distretto = distretto;
	}

	public District getDistretto() {
		return distretto;
	}

	public int getId_agente() {
		return id_agente;
	}

	public void setId_agente(int id_agente) {
		this.id_agente = id_agente;
	}

	public TIPO getTipo() {
		return tipo;
	}

	public LocalDateTime getOrainizio() {
		return orainizio;
	}

	public int getDurataevento() {
		return durataevento;
	}

	public LocalDateTime getOrafine() {
		return orafine;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((orainizio == null) ? 0 : orainizio.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
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
		Evento other = (Evento) obj;
		if (orainizio == null) {
			if (other.orainizio != null)
				return false;
		} else if (!orainizio.equals(other.orainizio))
			return false;
		if (tipo != other.tipo)
			return false;
		return true;
	}

	@Override
	public int compareTo(Evento arg0) {
		return this.orainizio.compareTo(arg0.getOrainizio());
	}

	@Override
	public String toString() {
		return "Evento [tipo=" + tipo + ", orainizio=" + orainizio + ", durataevento=" + durataevento + ", orafine="
				+ orafine + ", id_agente=" + id_agente + ", distretto=" + distretto + "]";
	}

}
