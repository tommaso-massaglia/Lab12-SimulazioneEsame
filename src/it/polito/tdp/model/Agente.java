package it.polito.tdp.model;

public class Agente{

	private int id_agente;
	private boolean libero;
	private District distretto_attuale;

	public Agente(int id_agente, boolean libero, District distretto_attuale) {
		this.id_agente = id_agente;
		this.libero = libero;
		this.distretto_attuale = distretto_attuale;
	}

	public int getId_agente() {
		return id_agente;
	}

	public void setId_agente(int id_agente) {
		this.id_agente = id_agente;
	}

	public boolean isLibero() {
		return libero;
	}

	public void setLibero(boolean libero) {
		this.libero = libero;
	}

	public District getDistretto_attuale() {
		return distretto_attuale;
	}

	public void setDistretto_attuale(District distretto_attuale) {
		this.distretto_attuale = distretto_attuale;
	}

	@Override
	public String toString() {
		return "Agente [id_agente=" + id_agente + ", libero=" + libero + ", distretto_attuale=" + distretto_attuale
				+ "]";
	}

}
