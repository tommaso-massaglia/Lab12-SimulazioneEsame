package it.polito.tdp.model;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();
		
		model.creaGrafoDistretti(2015);
		System.out.println(model.getElencoDistretti());
		model.simula(10, 1, 9, 2015);
	}

}
